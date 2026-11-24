/**
 * ARAL MATH for BAGACAY ES — Central Google Sheets Sync Bridge (Grades 2 to 6)
 * Account: franz.padojinog@gmail.com
 *
 * Sheet Tabs (in exact order 2 to 6):
 *   Grade 2 (36 worksheets)
 *   Grade 3 (24 worksheets)
 *   Grade 4 (34 worksheets)
 *   Grade 5 (32 worksheets)
 *   Grade 6 (35 worksheets)
 */

const TEACHER_EMAIL = "franz.padojinog@gmail.com";
const FIRST_LEARNER_ROW = 4;

const GRADE_ORDER = ["GRADE_2", "GRADE_3", "GRADE_4", "GRADE_5", "GRADE_6"];

const GRADE_CONFIGS = {
  GRADE_2: { tabName: "Grade 2", shortLabel: "Grade 2", totalWorksheets: 36, activeWorksheets: 36, legacyTabs: [] },
  GRADE_3: { tabName: "Grade 3", shortLabel: "Grade 3", totalWorksheets: 24, activeWorksheets: 24, legacyTabs: [] },
  GRADE_4: { tabName: "Grade 4", shortLabel: "Grade 4", totalWorksheets: 34, activeWorksheets: 34, legacyTabs: ["LearnerScores"] },
  GRADE_5: { tabName: "Grade 5", shortLabel: "Grade 5", totalWorksheets: 32, activeWorksheets: 32, legacyTabs: ["Grade5_Scores"] },
  GRADE_6: { tabName: "Grade 6", shortLabel: "Grade 6", totalWorksheets: 35, activeWorksheets: 35, legacyTabs: ["Grade6_Scores"] }
};

function setupAllGradeSheets() {
  const ss = SpreadsheetApp.getActiveSpreadsheet();
  ensureAllGradeSheetsInOrder(ss);
}

function doGet(e) {
  try {
    const params = (e && e.parameter) ? e.parameter : {};
    const action = params.action || "readAllGrades";
    const ss = SpreadsheetApp.getActiveSpreadsheet();
    ensureAllGradeSheetsInOrder(ss);

    if (action === "readAll") {
      const gradeKey = params.grade || "GRADE_4";
      return jsonResponse({
        ok: true,
        spreadsheetUrl: ss.getUrl(),
        grade: gradeKey,
        learners: readLearnersForGrade(ss, gradeKey)
      });
    }

    const byGrade = {};
    GRADE_ORDER.forEach(function (gradeKey) {
      byGrade[gradeKey] = readLearnersForGrade(ss, gradeKey);
    });

    return jsonResponse({
      ok: true,
      spreadsheetUrl: ss.getUrl(),
      byGrade: byGrade
    });
  } catch (err) {
    return jsonResponse({
      ok: false,
      error: err && err.message ? err.message : String(err)
    });
  }
}

function doPost(e) {
  const lock = LockService.getScriptLock();
  lock.waitLock(15000);
  try {
    const raw = e && e.postData && e.postData.contents ? e.postData.contents : "{}";
    const body = JSON.parse(raw);
    const action = body.action || "upsertLearner";
    const ss = SpreadsheetApp.getActiveSpreadsheet();
    ensureAllGradeSheetsInOrder(ss);

    if (action === "readAllGrades") {
      const byGrade = {};
      GRADE_ORDER.forEach(function (gradeKey) {
        byGrade[gradeKey] = readLearnersForGrade(ss, gradeKey);
      });
      return jsonResponse({
        ok: true,
        spreadsheetUrl: ss.getUrl(),
        byGrade: byGrade
      });
    }

    if (action === "upsertLearner") {
      const gradeKey = body.grade || "GRADE_4";
      const rowNumber = upsertLearnerRow(ss, gradeKey, body);
      return jsonResponse({
        ok: true,
        spreadsheetUrl: ss.getUrl(),
        grade: gradeKey,
        rowNumber: rowNumber
      });
    }

    return jsonResponse({ ok: false, error: "Unsupported action: " + action });
  } catch (err) {
    return jsonResponse({
      ok: false,
      error: err && err.message ? err.message : String(err)
    });
  } finally {
    lock.releaseLock();
  }
}

function ensureAllGradeSheetsInOrder(ss) {
  for (let i = 0; i < GRADE_ORDER.length; i++) {
    const gradeKey = GRADE_ORDER[i];
    const cfg = GRADE_CONFIGS[gradeKey];
    let sheet = ss.getSheetByName(cfg.tabName);

    // Migrate legacy tab names without losing existing records
    if (!sheet && cfg.legacyTabs && cfg.legacyTabs.length > 0) {
      for (let k = 0; k < cfg.legacyTabs.length; k++) {
        const legacy = ss.getSheetByName(cfg.legacyTabs[k]);
        if (legacy) {
          legacy.setName(cfg.tabName);
          sheet = legacy;
          break;
        }
      }
    }

    // Reuse default empty Sheet1 as Grade 2 if Grade 2 doesn't exist yet
    if (!sheet && gradeKey === "GRADE_2") {
      const defaultSheet = ss.getSheetByName("Sheet1");
      if (defaultSheet && defaultSheet.getLastRow() === 0) {
        defaultSheet.setName(cfg.tabName);
        sheet = defaultSheet;
      }
    }

    if (!sheet) {
      sheet = ss.insertSheet(cfg.tabName, i);
    }

    if (sheet.getIndex() !== i + 1) {
      ss.setActiveSheet(sheet);
      ss.moveActiveSheet(i + 1);
    }

    ensureGradeSheetHeaders(sheet, cfg);
  }
}

function ensureGradeSheet(ss, gradeKey) {
  const cfg = GRADE_CONFIGS[gradeKey] || GRADE_CONFIGS.GRADE_4;
  let sheet = ss.getSheetByName(cfg.tabName);
  if (!sheet) {
    ensureAllGradeSheetsInOrder(ss);
    sheet = ss.getSheetByName(cfg.tabName);
  }
  const totalCols = ensureGradeSheetHeaders(sheet, cfg);
  return { sheet: sheet, cfg: cfg, totalCols: totalCols };
}

function ensureGradeSheetHeaders(sheet, cfg) {
  const totalCols = 1 + cfg.totalWorksheets * 2 + 6;
  if (sheet.getMaxColumns() < totalCols) {
    sheet.insertColumnsAfter(sheet.getMaxColumns(), totalCols - sheet.getMaxColumns());
  }

  const headerCheck = String(sheet.getRange(3, 1).getValue() || "").trim();
  if (!headerCheck) {
    const row1 = new Array(totalCols).fill("");
    row1[0] = "ARAL MATH for BAGACAY ES — " + cfg.shortLabel + " Learner Progress & Mastery Tracker";

    const row2 = new Array(totalCols).fill("");
    row2[0] = "Teacher Target: " + TEACHER_EMAIL + " | Rule: >= 90% required to pass | 100% = Perfect ★";

    const row3 = new Array(totalCols).fill("");
    row3[0] = "Learner Profile (Name | Grade | School | Device Address)";
    for (let act = 1; act <= cfg.totalWorksheets; act++) {
      const scoreIdx = 1 + (act - 1) * 2;
      const tsIdx = 2 + (act - 1) * 2;
      row3[scoreIdx] = "Act " + act + " Score";
      row3[tsIdx] = "Act " + act + " Date & Time";
    }
    const metaStart = 1 + cfg.totalWorksheets * 2;
    row3[metaStart] = "Learner Name";
    row3[metaStart + 1] = "Grade Level";
    row3[metaStart + 2] = "School";
    row3[metaStart + 3] = "CP / Device Address";
    row3[metaStart + 4] = "Resume Position (Act:Item)";
    row3[metaStart + 5] = "Completed (>=90%)";

    sheet.getRange(1, 1, 3, totalCols).setValues([row1, row2, row3]);
    sheet.getRange(1, 1, 1, totalCols).setBackground("#1565C0").setFontColor("#FFFFFF").setFontWeight("bold");
    sheet.getRange(2, 1, 1, totalCols).setBackground("#E3F2FD").setFontColor("#0D47A1").setFontWeight("bold");
    sheet.getRange(3, 1, 1, totalCols).setBackground("#BBDEFB").setFontColor("#1A237E").setFontWeight("bold");
    sheet.setFrozenRows(3);
    sheet.setFrozenColumns(1);
  }
  return totalCols;
}

function normalizeName(name) {
  return String(name || "").trim().replace(/\s+/g, " ").toLowerCase();
}

function readLearnersForGrade(ss, gradeKey) {
  const info = ensureGradeSheet(ss, gradeKey);
  const sheet = info.sheet;
  const cfg = info.cfg;
  const totalCols = info.totalCols;
  const lastRow = sheet.getLastRow();
  if (lastRow < FIRST_LEARNER_ROW) return [];

  const values = sheet.getRange(FIRST_LEARNER_ROW, 1, lastRow - FIRST_LEARNER_ROW + 1, totalCols).getValues();
  const metaStart = 1 + cfg.totalWorksheets * 2;
  const learners = [];

  for (let i = 0; i < values.length; i++) {
    const row = values[i];
    const rowNumber = FIRST_LEARNER_ROW + i;
    const colA = String(row[0] || "").trim();
    const metaName = String(row[metaStart] || "").trim();
    if (!colA && !metaName) continue;

    const parts = colA.split("|").map(function (s) { return s.trim(); });
    const name = metaName || parts[0] || "";
    if (!name) continue;

    const gradeLevel = String(row[metaStart + 1] || "").trim() || parts[1] || cfg.shortLabel;
    const school = String(row[metaStart + 2] || "").trim() || parts[2] || "Bagacay Elementary School";
    const deviceAddress = String(row[metaStart + 3] || "").trim() || parts[3] || "";
    const resumeRaw = String(row[metaStart + 4] || "").trim();
    const resumeParts = resumeRaw.split(":");
    const lastWorksheetId = Math.max(1, Math.min(cfg.totalWorksheets, parseInt(resumeParts[0], 10) || 1));
    const lastItemIndex = Math.max(0, parseInt(resumeParts[1], 10) || 0);

    const activities = {};
    for (let act = 1; act <= cfg.totalWorksheets; act++) {
      const scoreIdx = 1 + (act - 1) * 2;
      const tsIdx = 2 + (act - 1) * 2;
      const scoreVal = String(row[scoreIdx] || "").trim();
      const tsVal = String(row[tsIdx] || "").trim();
      if (scoreVal) {
        activities[String(act)] = {
          score: scoreVal,
          timestamp: tsVal
        };
      }
    }

    learners.push({
      rowNumber: rowNumber,
      name: name,
      gradeLevel: gradeLevel,
      school: school,
      deviceAddress: deviceAddress,
      lastWorksheetId: lastWorksheetId,
      lastItemIndex: lastItemIndex,
      activities: activities
    });
  }
  return learners;
}

function upsertLearnerRow(ss, gradeKey, body) {
  const info = ensureGradeSheet(ss, gradeKey);
  const sheet = info.sheet;
  const cfg = info.cfg;
  const totalCols = info.totalCols;
  const metaStart = 1 + cfg.totalWorksheets * 2;

  const name = String(body.name || "").trim();
  if (!name) throw new Error("Missing learner name");
  const targetKey = normalizeName(name);

  let targetRow = parseInt(body.sheetRowNumber, 10) || 0;
  if (targetRow < FIRST_LEARNER_ROW) targetRow = 0;

  const lastRow = sheet.getLastRow();
  if (lastRow >= FIRST_LEARNER_ROW) {
    const existingRows = sheet.getRange(FIRST_LEARNER_ROW, 1, lastRow - FIRST_LEARNER_ROW + 1, totalCols).getValues();
    for (let i = 0; i < existingRows.length; i++) {
      const r = existingRows[i];
      const existingMetaName = String(r[metaStart] || "").trim();
      const existingColAName = String(r[0] || "").split("|")[0].trim();
      const existingKey = normalizeName(existingMetaName || existingColAName);
      if (existingKey && existingKey === targetKey) {
        targetRow = FIRST_LEARNER_ROW + i;
        break;
      }
    }
  }

  if (!targetRow) {
    targetRow = Math.max(FIRST_LEARNER_ROW, lastRow + 1);
  }

  const rowValues = sheet.getRange(targetRow, 1, 1, totalCols).getValues()[0];
  const gradeLevel = String(body.gradeLevel || cfg.shortLabel).trim();
  const school = String(body.school || "Bagacay Elementary School").trim();
  const deviceAddress = String(body.deviceAddress || "").trim();

  rowValues[0] = name + " | " + gradeLevel + " | " + school + " | " + deviceAddress;

  const activities = body.activities || {};
  Object.keys(activities).forEach(function (actKey) {
    const actId = parseInt(actKey, 10);
    if (actId >= 1 && actId <= cfg.totalWorksheets) {
      const entry = activities[actKey];
      const scoreIdx = 1 + (actId - 1) * 2;
      const tsIdx = 2 + (actId - 1) * 2;
      if (entry && entry.score) {
        rowValues[scoreIdx] = String(entry.score);
        rowValues[tsIdx] = String(entry.timestamp || "");
      }
    }
  });

  rowValues[metaStart] = name;
  rowValues[metaStart + 1] = gradeLevel;
  rowValues[metaStart + 2] = school;
  rowValues[metaStart + 3] = deviceAddress;
  rowValues[metaStart + 4] = String(body.resumePosition || "1:0");
  rowValues[metaStart + 5] = String(body.completedSummary || ("0/" + cfg.activeWorksheets));

  sheet.getRange(targetRow, 1, 1, totalCols).setValues([rowValues]);
  return targetRow;
}

function jsonResponse(payload) {
  return ContentService
    .createTextOutput(JSON.stringify(payload))
    .setMimeType(ContentService.MimeType.JSON);
}

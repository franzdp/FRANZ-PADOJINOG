package com.example.data.g5

import com.example.model.MathProblem

/**
 * Grade 5 Worksheets 30, 31, and 32 — Angles & Angle Classification.
 * Diagram IDs map directly to Grade5AnglesVisuals.kt:
 *  WS 30: 3001 (CHE), 3002 (ICK 1), 3003 (STH/P 3), 3004 (SQR), 3005 (ZXY 6), 3006 (NPRQ 1,2), 3007 (WUV), 3008 (ABC 9), 3009 (LOMN 3,4)
 *  WS 31: 3100 (KERI 4,5), 3107..3118 (single angles #7-#18), 3119 (WYUFMC intersecting)
 *  WS 32: 3201 (APSDBVQT), 3202 (TPQDSABV), 3203 (EPQABMRD), 3204 (HKLTGSNJ)
 */
object Grade5AnglesData {

  private fun angleName(
    item: Int,
    diagramId: Int,
    prompt: String,
    valid: List<String>,
    example: String,
    highlightIdx: Int = 0,
    hint: String,
  ) = MathProblem.AngleNameProblem(
    itemNumber = item,
    diagramId = diagramId,
    prompt = prompt,
    validAnswers = valid,
    exampleAnswer = example,
    highlightIndex = highlightIdx,
    hint = hint,
  )

  private fun singleKind(item: Int, diagramId: Int, answer: String, hint: String) =
    MathProblem.AngleKindProblem(
      itemNumber = item,
      diagramId = diagramId,
      prompt = "Identify the kind of angle shown:",
      isSingleClassify = true,
      options = listOf("Acute", "Right", "Obtuse"),
      validAnswers = listOf(answer),
      hint = hint,
    )

  private fun figureAngleKind(
    item: Int,
    diagramId: Int,
    prompt: String,
    options: List<String>,
    valid: List<String>,
    hint: String,
  ) = MathProblem.AngleKindProblem(
    itemNumber = item,
    diagramId = diagramId,
    prompt = prompt,
    isSingleClassify = false,
    options = options,
    validAnswers = valid,
    hint = hint,
  )

  private val cheValid = listOf("∠CHE", "∠EHC", "∠H")
  private val ickValid = listOf("∠ICK", "∠KCI", "∠C", "∠1")
  private val sqrValid = listOf("∠SQR", "∠RQS", "∠Q")
  private val zxyValid = listOf("∠ZXY", "∠YXZ", "∠X", "∠6")
  private val wuvValid = listOf("∠WUV", "∠VUW", "∠U")
  private val abcValid = listOf("∠ABC", "∠CBA", "∠B", "∠9")

  private val keriAll = listOf(
    "∠KRE", "∠ERK",
    "∠ERN", "∠NRE", "∠4",
    "∠NRI", "∠IRN", "∠5",
    "∠KRN", "∠NRK",
    "∠ERI", "∠IRE",
    "∠KRI", "∠IRK",
  )

  val ws30: List<MathProblem> = listOf(
    angleName(1, 3001, "Name ∠CHE (Way 1 of 3):", cheValid, "∠CHE", 1, "Put the vertex letter in the middle of 3 letters (arm–vertex–arm), or use the vertex letter alone."),
    angleName(2, 3001, "Name ∠CHE in another way (Way 2 of 3):", cheValid, "∠EHC", 2, "Reverse the two outer arm letters (keeping the vertex in the middle) or use the single vertex letter."),
    angleName(3, 3001, "Name ∠CHE in a third way (Way 3 of 3):", cheValid, "∠H", 3, "Try the single vertex letter name (∠ + vertex) or a 3-letter form."),
    angleName(4, 3002, "Name the angle ICK (1) — Way 1 of 4:", ickValid, "∠ICK", 1, "Place the middle vertex letter between the two outer arm letters (or use vertex/number)."),
    angleName(5, 3002, "Name angle ICK (1) — Way 2 of 4:", ickValid, "∠KCI", 2, "Try reversing the outer letters, using only the vertex letter, or using the interior number."),
    angleName(6, 3002, "Name angle ICK (1) — Way 3 of 4:", ickValid, "∠C", 3, "Try the single vertex letter name or the number inside the angle."),
    angleName(7, 3002, "Name angle ICK (1) — Way 4 of 4:", ickValid, "∠1", 4, "Look inside the angle arc — you can name it with ∠ followed by that number!"),
    angleName(8, 3003, "Name one angle shown in diagram S-T-P-H (in any valid way):", listOf("∠STP", "∠PTS", "∠3", "∠PTH", "∠HTP", "∠STH", "∠HTS"), "∠3", 1, "When angles share a vertex, use 3 letters (arm–vertex–arm) or the angle number inside the arc."),
    angleName(9, 3004, "Name angle SQR — Way 1 of 3:", sqrValid, "∠SQR", 1, "Put the vertex in the middle of 3 letters, or use the vertex letter alone."),
    angleName(10, 3004, "Name angle SQR — Way 2 of 3:", sqrValid, "∠RQS", 2, "Reverse the outer arm letters (vertex stays in the middle) or use the vertex letter."),
    angleName(11, 3004, "Name angle SQR — Way 3 of 3:", sqrValid, "∠Q", 3, "Use ∠ followed by the single vertex letter!"),
    angleName(12, 3005, "Name angle ZXY (6) — Way 1 of 4:", zxyValid, "∠ZXY", 1, "Put the vertex in the middle of 3 letters, or use the vertex letter or interior number."),
    angleName(13, 3005, "Name angle ZXY (6) — Way 2 of 4:", zxyValid, "∠YXZ", 2, "Reverse the outer letters (keeping the vertex in the middle), or use the vertex/number."),
    angleName(14, 3005, "Name angle ZXY (6) — Way 3 of 4:", zxyValid, "∠X", 3, "Try the single vertex letter or the interior number."),
    angleName(15, 3005, "Name angle ZXY (6) — Way 4 of 4:", zxyValid, "∠6", 4, "Look inside the angle arc — name it using ∠ + that number!"),
    angleName(16, 3006, "Name the left angle (labeled 1) in N-P-R-Q:", listOf("∠NPR", "∠RPN", "∠1"), "∠1", 1, "Trace the two rays forming the left angle and put the shared vertex in the middle (or use its interior number)."),
    angleName(17, 3006, "Name the right angle (labeled 2) in N-P-R-Q:", listOf("∠RPQ", "∠QPR", "∠2"), "∠2", 2, "Trace the two rays forming the right angle and put the shared vertex in the middle (or use its interior number)."),
    angleName(18, 3007, "Name angle WUV — Way 1 of 3:", wuvValid, "∠WUV", 1, "Put the vertex letter in the middle of 3 letters, or use the vertex letter alone."),
    angleName(19, 3007, "Name angle WUV — Way 2 of 3:", wuvValid, "∠VUW", 2, "Reverse the outer arm letters (vertex stays in the middle) or use the vertex letter."),
    angleName(20, 3007, "Name angle WUV — Way 3 of 3:", wuvValid, "∠U", 3, "Use ∠ followed by the single vertex letter!"),
    angleName(21, 3008, "Name angle ABC (9) — Way 1 of 4:", abcValid, "∠ABC", 1, "Put the vertex in the middle of 3 letters, or use the vertex letter or interior number."),
    angleName(22, 3008, "Name angle ABC (9) — Way 2 of 4:", abcValid, "∠CBA", 2, "Reverse the outer letters (keeping the vertex in the middle), or use the vertex/number."),
    angleName(23, 3008, "Name angle ABC (9) — Way 3 of 4:", abcValid, "∠B", 3, "Try the single vertex letter or the interior number."),
    angleName(24, 3008, "Name angle ABC (9) — Way 4 of 4:", abcValid, "∠9", 4, "Look inside the angle arc — name it using ∠ + that number!"),
    angleName(25, 3009, "Name the left angle (labeled 3) in L-M-O-N:", listOf("∠LMO", "∠OML", "∠3"), "∠3", 1, "Trace the two rays forming the left angle and put the shared vertex in the middle (or use its interior number)."),
    angleName(26, 3009, "Name the right angle (labeled 4) in L-M-O-N:", listOf("∠OMN", "∠NMO", "∠4"), "∠4", 2, "Trace the two rays forming the right angle and put the shared vertex in the middle (or use its interior number)."),
  )

  private val wyufmcAcute = listOf("∠EYR", "∠RYE", "∠YER", "∠REY", "∠WEY", "∠FYU", "∠UYF", "∠MYW", "∠WYM")
  private val wyufmcObtuse = listOf("∠WER", "∠REW", "∠WEC", "∠CEW", "∠RYU", "∠UYR", "∠MYU", "∠UYM", "∠WYF", "∠FYW")
  private val wyufmcRight = listOf("∠ERY", "∠YRE", "∠YRC", "∠CRY", "∠CRM", "∠MRC", "∠MRE", "∠ERM", "∠ERF", "∠FRE", "∠FRC", "∠CRF")
  private val wyufmcChoices = listOf("∠EYR", "∠YER", "∠FYU", "∠WER", "∠RYU", "∠ERY", "∠YRC", "∠CRM", "∠MRE")

  val ws31: List<MathProblem> = listOf(
    angleName(1, 3100, "Name Angle 1 of 6 in the K-E-N-I / R figure:", listOf("∠KRE", "∠ERK"), "∠KRE", 1, "Trace the two highlighted rays from vertex R and write ∠ followed by outer point – R – outer point."),
    angleName(2, 3100, "Name Angle 2 of 6 in the K-E-N-I / R figure:", listOf("∠ERN", "∠NRE", "∠4"), "∠ERN", 2, "Trace the two highlighted rays from vertex R and put R in the middle (or use its interior number label)."),
    angleName(3, 3100, "Name Angle 3 of 6 in the K-E-N-I / R figure:", listOf("∠NRI", "∠IRN", "∠5"), "∠NRI", 3, "Trace the two highlighted rays from vertex R and put R in the middle (or use its interior number label)."),
    angleName(4, 3100, "Name Angle 4 of 6 in the K-E-N-I / R figure:", listOf("∠KRN", "∠NRK"), "∠KRN", 4, "Combine two adjacent small angles by tracing the outer two rays from vertex R!"),
    angleName(5, 3100, "Name Angle 5 of 6 in the K-E-N-I / R figure:", listOf("∠ERI", "∠IRE"), "∠ERI", 5, "Combine two adjacent small angles by tracing the outer two rays from vertex R!"),
    angleName(6, 3100, "Name Angle 6 of 6 (largest) in the K-E-N-I / R figure:", listOf("∠KRI", "∠IRK") + keriAll, "∠KRI", 6, "Trace the two outermost rays from vertex R and put R in the middle."),
    // 7-18 single angle classification
    singleKind(7, 3107, "Acute", "Compare the opening to a 90° square corner: smaller than 90° is Acute, exact square corner is Right, wider than 90° is Obtuse."),
    singleKind(8, 3108, "Right", "Compare the opening to a 90° square corner: look for a square corner marker (90°) vs narrower or wider openings."),
    singleKind(9, 3109, "Acute", "Compare the opening to a 90° square corner: smaller than 90° is Acute, exact square corner is Right, wider than 90° is Obtuse."),
    singleKind(10, 3110, "Acute", "Look closely at the opening compared to a 90° square corner — is it narrower, exact, or wider?"),
    singleKind(11, 3111, "Obtuse", "Compare the opening to a 90° square corner: smaller than 90° is Acute, exact square corner is Right, wider than 90° is Obtuse."),
    singleKind(12, 3112, "Acute", "Compare the opening to a 90° square corner: smaller than 90° is Acute, exact square corner is Right, wider than 90° is Obtuse."),
    singleKind(13, 3113, "Right", "Compare the opening to a 90° square corner: look for a square corner marker (90°) vs narrower or wider openings."),
    singleKind(14, 3114, "Obtuse", "Compare the opening to a 90° square corner: smaller than 90° is Acute, exact square corner is Right, wider than 90° is Obtuse."),
    singleKind(15, 3115, "Acute", "Compare the opening to a 90° square corner: smaller than 90° is Acute, exact square corner is Right, wider than 90° is Obtuse."),
    singleKind(16, 3116, "Acute", "Look closely at the opening compared to a 90° square corner — is it narrower, exact, or wider?"),
    singleKind(17, 3117, "Right", "Compare the opening to a 90° square corner: look for a square corner marker (90°) vs narrower or wider openings."),
    singleKind(18, 3118, "Acute", "Compare the opening to a 90° square corner: smaller than 90° is Acute, exact square corner is Right, wider than 90° is Obtuse."),
    // 19-27 WYUFMC
    figureAngleKind(19, 3119, "In figure WYUFMC, pick an ACUTE angle (#1 of 3):", wyufmcChoices, wyufmcAcute, "An ACUTE angle opens narrower than a 90° square corner — trace the sharp corners in the diagram."),
    figureAngleKind(20, 3119, "In figure WYUFMC, pick a second ACUTE angle (#2 of 3):", wyufmcChoices, wyufmcAcute, "Look for another sharp corner opening less than 90°."),
    figureAngleKind(21, 3119, "In figure WYUFMC, pick a third ACUTE angle (#3 of 3):", wyufmcChoices, wyufmcAcute, "Look for another sharp corner opening less than 90°."),
    figureAngleKind(22, 3119, "In figure WYUFMC, pick an OBTUSE angle (#1 of 2):", wyufmcChoices, wyufmcObtuse, "An OBTUSE angle opens wider than a 90° square corner — check along straight lines where a slanted ray leans back."),
    figureAngleKind(23, 3119, "In figure WYUFMC, pick a second OBTUSE angle (#2 of 2):", wyufmcChoices, wyufmcObtuse, "Look for another wide angle (> 90°) along a straight line."),
    figureAngleKind(24, 3119, "In figure WYUFMC, pick a RIGHT angle (#1 of 4):", wyufmcChoices, wyufmcRight, "A RIGHT angle forms a 90° square corner — check where perpendicular lines meet!"),
    figureAngleKind(25, 3119, "In figure WYUFMC, pick a second RIGHT angle (#2 of 4):", wyufmcChoices, wyufmcRight, "Look for another 90° square corner where perpendicular lines cross."),
    figureAngleKind(26, 3119, "In figure WYUFMC, pick a third RIGHT angle (#3 of 4):", wyufmcChoices, wyufmcRight, "Look for another 90° square corner where perpendicular lines cross."),
    figureAngleKind(27, 3119, "In figure WYUFMC, pick a fourth RIGHT angle (#4 of 4):", wyufmcChoices, wyufmcRight, "Pick the remaining 90° square corner where perpendicular lines cross."),
  )

  private val fig1Choices = listOf("∠AST", "∠TSB", "∠BSQ", "∠CSV", "∠ASQ", "∠TSD", "∠TSQ", "∠QSV", "∠VSP", "∠PST")
  private val fig1Acute = listOf("∠AST", "∠TSA", "∠TSB", "∠BST", "∠BSQ", "∠QSB", "∠QSC", "∠CSQ", "∠CSV", "∠VSC", "∠VSD", "∠DSV", "∠DSP", "∠PSD", "∠PSA", "∠ASP")
  private val fig1Obtuse = listOf("∠ASQ", "∠QSA", "∠TSD", "∠DST", "∠BSV", "∠VSB", "∠PSC", "∠CSP", "∠PSB", "∠BSP", "∠ASD", "∠DSA")
  private val fig1Right = listOf("∠TSQ", "∠QST", "∠QSV", "∠VSQ", "∠VSP", "∠PSV", "∠PST", "∠TSP")

  private val fig2Choices = listOf("∠TPS", "∠TSA", "∠BSQ", "∠VSD", "∠PSB", "∠ASV", "∠PSV", "∠QSV", "∠ASB", "∠DSB")
  private val fig2Acute = listOf("∠BSQ", "∠QSB", "∠QSA", "∠ASQ", "∠VSD", "∠DSV", "∠DSP", "∠PSD", "∠TPS", "∠PST", "∠TSP", "∠TSV")
  private val fig2Obtuse = listOf("∠PSB", "∠BSP", "∠ASV", "∠VSA", "∠TSQ", "∠QST", "∠PSA", "∠ASP")
  private val fig2Right = listOf("∠PSV", "∠VSP", "∠QSV", "∠VSQ", "∠TSA", "∠AST", "∠ASB", "∠BSA", "∠DSB", "∠BSD", "∠TSD", "∠DST")

  private val fig3Choices = listOf("∠PAR", "∠BAQ", "∠EAR", "∠PAB", "∠RAQ", "∠ERA", "∠ARD", "∠DRM", "∠MRE")
  private val fig3Acute = listOf("∠PAR", "∠RAP", "∠BAQ", "∠QAB", "∠EAR", "∠RAE")
  private val fig3Obtuse = listOf("∠PAB", "∠BAP", "∠RAQ", "∠QAR", "∠EAP", "∠PAE")
  private val fig3Right = listOf("∠ERA", "∠ARE", "∠ARD", "∠DRA", "∠DRM", "∠MRD", "∠MRE", "∠ERM", "∠ERB", "∠BRE", "∠BRD", "∠DRB")

  private val fig4Choices = listOf("∠KTS", "∠NTL", "∠HTS", "∠KTN", "∠STL", "∠HST", "∠TSJ", "∠JSG", "∠GSH")
  private val fig4Acute = listOf("∠KTS", "∠STK", "∠NTL", "∠LTN", "∠HTS", "∠STH", "∠KTH", "∠HTK")
  private val fig4Obtuse = listOf("∠KTN", "∠NTK", "∠STL", "∠LTS", "∠LTG", "∠GTL")
  private val fig4Right = listOf("∠HST", "∠TSH", "∠TSJ", "∠JST", "∠JSG", "∠GSJ", "∠GSH", "∠HSG", "∠HSN", "∠NSH", "∠NSJ", "∠JSN")

  val ws32: List<MathProblem> = listOf(
    // Figure 1 (items 1-9)
    figureAngleKind(1, 3201, "Diagram 1 (APSDBVQT): Name ACUTE angle #1 of 3:", fig1Choices, fig1Acute, "Trace a sharp wedge opening narrower than a 90° square corner."),
    figureAngleKind(2, 3201, "Diagram 1 (APSDBVQT): Name ACUTE angle #2 of 3:", fig1Choices, fig1Acute, "Look for another small wedge (< 90°) around the center vertex."),
    figureAngleKind(3, 3201, "Diagram 1 (APSDBVQT): Name ACUTE angle #3 of 3:", fig1Choices, fig1Acute, "Pick a third acute angle (< 90°) around the center vertex."),
    figureAngleKind(4, 3201, "Diagram 1 (APSDBVQT): Name OBTUSE angle #1 of 2:", fig1Choices, fig1Obtuse, "Look for a wide angle opening wider than a 90° square corner."),
    figureAngleKind(5, 3201, "Diagram 1 (APSDBVQT): Name OBTUSE angle #2 of 2:", fig1Choices, fig1Obtuse, "Pick a second wide angle (> 90°) around the center vertex."),
    figureAngleKind(6, 3201, "Diagram 1 (APSDBVQT): Name RIGHT angle #1 of 4:", fig1Choices, fig1Right, "Look for an exact 90° square corner where perpendicular lines meet."),
    figureAngleKind(7, 3201, "Diagram 1 (APSDBVQT): Name RIGHT angle #2 of 4:", fig1Choices, fig1Right, "Pick another 90° square corner formed by perpendicular lines."),
    figureAngleKind(8, 3201, "Diagram 1 (APSDBVQT): Name RIGHT angle #3 of 4:", fig1Choices, fig1Right, "Pick a 3rd 90° square corner formed by perpendicular lines."),
    figureAngleKind(9, 3201, "Diagram 1 (APSDBVQT): Name RIGHT angle #4 of 4:", fig1Choices, fig1Right, "Pick the 4th 90° square corner formed by perpendicular lines."),

    // Figure 2 (items 10-18)
    figureAngleKind(10, 3202, "Diagram 2 (TPQDSABV): Name ACUTE angle #1 of 3:", fig2Choices, fig2Acute, "Trace a sharp corner opening narrower than a 90° square corner."),
    figureAngleKind(11, 3202, "Diagram 2 (TPQDSABV): Name ACUTE angle #2 of 3:", fig2Choices, fig2Acute, "Pick a second sharp angle (< 90°)."),
    figureAngleKind(12, 3202, "Diagram 2 (TPQDSABV): Name ACUTE angle #3 of 3:", fig2Choices, fig2Acute, "Pick a third sharp angle (< 90°)."),
    figureAngleKind(13, 3202, "Diagram 2 (TPQDSABV): Name OBTUSE angle #1 of 2:", fig2Choices, fig2Obtuse, "Trace a wide corner opening wider than a 90° square corner."),
    figureAngleKind(14, 3202, "Diagram 2 (TPQDSABV): Name OBTUSE angle #2 of 2:", fig2Choices, fig2Obtuse, "Pick a second wide angle (> 90°)."),
    figureAngleKind(15, 3202, "Diagram 2 (TPQDSABV): Name RIGHT angle #1 of 4:", fig2Choices, fig2Right, "Look for an exact 90° square corner where perpendicular lines cross."),
    figureAngleKind(16, 3202, "Diagram 2 (TPQDSABV): Name RIGHT angle #2 of 4:", fig2Choices, fig2Right, "Pick another 90° square corner in Diagram 2."),
    figureAngleKind(17, 3202, "Diagram 2 (TPQDSABV): Name RIGHT angle #3 of 4:", fig2Choices, fig2Right, "Pick a 3rd 90° square corner in Diagram 2."),
    figureAngleKind(18, 3202, "Diagram 2 (TPQDSABV): Name RIGHT angle #4 of 4:", fig2Choices, fig2Right, "Pick a 4th 90° square corner in Diagram 2."),

    // Figure 3 (items 19-27)
    figureAngleKind(19, 3203, "Diagram 3 (EPQABMRD): Name ACUTE angle #1 of 3:", fig3Choices, fig3Acute, "Trace a sharp corner opening narrower than 90°."),
    figureAngleKind(20, 3203, "Diagram 3 (EPQABMRD): Name ACUTE angle #2 of 3:", fig3Choices, fig3Acute, "Pick a second sharp angle (< 90°)."),
    figureAngleKind(21, 3203, "Diagram 3 (EPQABMRD): Name ACUTE angle #3 of 3:", fig3Choices, fig3Acute, "Pick a third sharp angle (< 90°)."),
    figureAngleKind(22, 3203, "Diagram 3 (EPQABMRD): Name OBTUSE angle #1 of 2:", fig3Choices, fig3Obtuse, "Check along the straight line for a wide angle opening more than 90°."),
    figureAngleKind(23, 3203, "Diagram 3 (EPQABMRD): Name OBTUSE angle #2 of 2:", fig3Choices, fig3Obtuse, "Look for another wide angle (> 90°) along the straight line."),
    figureAngleKind(24, 3203, "Diagram 3 (EPQABMRD): Name RIGHT angle #1 of 4:", fig3Choices, fig3Right, "Look for a 90° square corner where the perpendicular lines meet."),
    figureAngleKind(25, 3203, "Diagram 3 (EPQABMRD): Name RIGHT angle #2 of 4:", fig3Choices, fig3Right, "Pick another 90° corner where the perpendicular lines meet."),
    figureAngleKind(26, 3203, "Diagram 3 (EPQABMRD): Name RIGHT angle #3 of 4:", fig3Choices, fig3Right, "Pick the 3rd 90° corner where the perpendicular lines meet."),
    figureAngleKind(27, 3203, "Diagram 3 (EPQABMRD): Name RIGHT angle #4 of 4:", fig3Choices, fig3Right, "Pick the 4th 90° corner where the perpendicular lines meet."),

    // Figure 4 (items 28-36)
    figureAngleKind(28, 3204, "Diagram 4 (HKLTGSNJ): Name ACUTE angle #1 of 3:", fig4Choices, fig4Acute, "Trace a sharp corner opening narrower than 90°."),
    figureAngleKind(29, 3204, "Diagram 4 (HKLTGSNJ): Name ACUTE angle #2 of 3:", fig4Choices, fig4Acute, "Pick a second sharp angle (< 90°)."),
    figureAngleKind(30, 3204, "Diagram 4 (HKLTGSNJ): Name ACUTE angle #3 of 3:", fig4Choices, fig4Acute, "Pick a third sharp angle (< 90°)."),
    figureAngleKind(31, 3204, "Diagram 4 (HKLTGSNJ): Name OBTUSE angle #1 of 2:", fig4Choices, fig4Obtuse, "Check along the slanted line for a wide angle opening more than 90°."),
    figureAngleKind(32, 3204, "Diagram 4 (HKLTGSNJ): Name OBTUSE angle #2 of 2:", fig4Choices, fig4Obtuse, "Look for another wide angle (> 90°) along the slanted line."),
    figureAngleKind(33, 3204, "Diagram 4 (HKLTGSNJ): Name RIGHT angle #1 of 4:", fig4Choices, fig4Right, "Look for a 90° square corner where the perpendicular lines meet."),
    figureAngleKind(34, 3204, "Diagram 4 (HKLTGSNJ): Name RIGHT angle #2 of 4:", fig4Choices, fig4Right, "Pick another 90° corner where the perpendicular lines meet."),
    figureAngleKind(35, 3204, "Diagram 4 (HKLTGSNJ): Name RIGHT angle #3 of 4:", fig4Choices, fig4Right, "Pick the 3rd 90° corner where the perpendicular lines meet."),
    figureAngleKind(36, 3204, "Diagram 4 (HKLTGSNJ): Name RIGHT angle #4 of 4:", fig4Choices, fig4Right, "Pick the 4th 90° corner where the perpendicular lines meet."),
  )
}

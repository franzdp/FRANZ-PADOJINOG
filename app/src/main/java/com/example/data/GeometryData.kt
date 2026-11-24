package com.example.data

import com.example.model.CongruentSubType
import com.example.model.GeoFigureType
import com.example.model.MathProblem

object GeometryData {

  fun getWorksheet21(): List<MathProblem> {
    val list = mutableListOf<MathProblem>()
    val defs = listOf(
      Triple(1, "It is represented by a dot.", "Point") to listOf("Point", "Line", "Ray", "Line segment"),
      Triple(2, "It is the pointed end of an arrow.", "Arrowhead") to listOf("Arrowhead", "Endpoint", "Point", "Ray"),
      Triple(3, "It is formed by many points that extend in opposite directions as represented by the arrowheads.", "Line") to listOf("Point", "Line", "Ray", "Line segment"),
      Triple(4, "It is a part of a line that has one endpoint and one arrowhead.", "Ray") to listOf("Point", "Line", "Ray", "Line segment"),
      Triple(5, "It is a part of a line that has two endpoints.", "Line segment") to listOf("Point", "Line", "Ray", "Line segment"),
    )
    defs.forEach { (meta, opts) ->
      list += MathProblem.GeometryIdentifyProblem(
        itemNumber = meta.first, prompt = meta.second, figure = null, options = opts, answer = meta.third,
        hint = "Think about dots, endpoints, and arrowheads.",
      )
    }
    val figOpts = listOf("Point", "Line", "Line segment", "Ray")
    val figs = listOf(
      Triple(6, GeoFigureType.POINT_D, "Point"),
      Triple(7, GeoFigureType.RAY_RIGHT, "Ray"),
      Triple(8, GeoFigureType.SEGMENT_HORIZ, "Line segment"),
      Triple(9, GeoFigureType.LINE_HORIZ, "Line"),
      Triple(10, GeoFigureType.RAY_DOWN_LEFT, "Ray"),
      Triple(11, GeoFigureType.SEGMENT_TILTED, "Line segment"),
      Triple(12, GeoFigureType.POINT_A, "Point"),
      Triple(13, GeoFigureType.LINE_DIAG, "Line"),
      Triple(14, GeoFigureType.POINT_F, "Point"),
    )
    figs.forEach { (num, fig, ans) ->
      list += MathProblem.GeometryIdentifyProblem(
        itemNumber = num, prompt = "Look at the figure. Is it a point, line, line segment, or ray?",
        figure = fig, options = figOpts, answer = ans,
        hint = "Count endpoints (dots) and arrowheads!",
      )
    }
    val namedFigs = listOf(
      Triple(15, GeoFigureType.RAY_MN, "Ray MN") to listOf("Ray MN", "Line MN", "Segment MN", "Point M"),
      Triple(16, GeoFigureType.POINT_P, "Point P") to listOf("Point P", "Ray P", "Line P", "Segment P"),
      Triple(17, GeoFigureType.LINE_QR, "Line QR") to listOf("Line QR", "Ray QR", "Segment QR", "Point Q"),
      Triple(18, GeoFigureType.SEGMENT_ST, "Line segment ST") to listOf("Line segment ST", "Line ST", "Ray ST", "Point S"),
    )
    namedFigs.forEach { (meta, opts) ->
      list += MathProblem.GeometryIdentifyProblem(
        itemNumber = meta.first, prompt = "Name each figure using the point(s).",
        figure = meta.second, options = opts, answer = meta.third,
        hint = "Check the letters on the figure and whether it has dots or arrows.",
      )
    }
    // 19-26: Find two of each geometric figure from the box
    list += MathProblem.GeometryBoxFindProblem(19, "Line (1st)", listOf("BC", "CB", "FG", "GF"), "Lines extend infinitely with arrowheads at BOTH ends.")
    list += MathProblem.GeometryBoxFindProblem(20, "Line (2nd)", listOf("FG", "GF", "BC", "CB"), "Find another straight path with arrowheads on both ends.")
    list += MathProblem.GeometryBoxFindProblem(21, "Ray (1st)", listOf("ED", "BC", "CB", "FG", "GF"), "A ray starts at an endpoint dot and extends through one arrowhead.")
    list += MathProblem.GeometryBoxFindProblem(22, "Ray (2nd)", listOf("BC", "CB", "FG", "GF", "ED"), "Look for an endpoint dot connected to a single arrow direction.")
    list += MathProblem.GeometryBoxFindProblem(23, "Point (1st)", listOf("A", "J", "M", "B", "C", "D", "E", "F", "G", "H", "I", "K", "L", "N", "O"), "Tap any single labeled dot in the box.")
    list += MathProblem.GeometryBoxFindProblem(24, "Point (2nd)", listOf("J", "M", "A", "B", "C", "D", "E", "F", "G", "H", "I", "K", "L", "N", "O"), "Tap another single labeled dot in the box.")
    list += MathProblem.GeometryBoxFindProblem(25, "Line Segment (1st)", listOf("HI", "IH", "KL", "LK", "NO", "ON"), "Line segments have two endpoint dots and NO arrowheads.")
    list += MathProblem.GeometryBoxFindProblem(26, "Line Segment (2nd)", listOf("KL", "LK", "NO", "ON", "HI", "IH"), "Pick another two-endpoint segment with no arrowheads.")

    // 27-30: Draw each geometric figure inside the box
    list += MathProblem.GeometryDrawProblem(27, "Draw point X inside the box.", "Tap to place a neat dot and label it X.", hint = "A point is a single dot.")
    list += MathProblem.GeometryDrawProblem(28, "Draw line YZ using the ruler guide.", "Draw a straight line with arrowheads at both ends.", hint = "A line has arrows on both ends.")
    list += MathProblem.GeometryDrawProblem(29, "Draw line segment PQ using the ruler guide.", "Draw a straight path connecting two endpoint dots P and Q.", hint = "Line segments end at two dots.")
    list += MathProblem.GeometryDrawProblem(30, "Draw ray RS using the ruler guide.", "Start at dot R and draw an arrow passing through S.", hint = "A ray has 1 endpoint and 1 arrowhead.")
    return list
  }

  fun getWorksheet22(): List<MathProblem> {
    val list = mutableListOf<MathProblem>()
    val pOpts = listOf("Parallel", "Not parallel")
    val pItems = listOf(
      Triple(1, GeoFigureType.PAIR_PARALLEL_TILT_DOWN, "Parallel"),
      Triple(2, GeoFigureType.PAIR_NOT_PARALLEL_1, "Not parallel"),
      Triple(3, GeoFigureType.PAIR_NOT_PARALLEL_2, "Not parallel"),
      Triple(4, GeoFigureType.PAIR_PARALLEL_TILT_UP, "Parallel"),
    )
    pItems.forEach { (num, fig, ans) ->
      list += MathProblem.GeometryIdentifyProblem(
        num, "Are these two lines parallel or not parallel?", fig, pOpts, ans,
        "Parallel lines stay the same distance apart and never cross, even if extended.",
      )
    }
    val kOpts = listOf("Parallel", "Intersecting", "Perpendicular")
    val kItems = listOf(
      Triple(5, GeoFigureType.PAIR_INTERSECT_X, "Intersecting"),
      Triple(6, GeoFigureType.PAIR_PERP_CROSS, "Perpendicular"),
      Triple(7, GeoFigureType.PAIR_PARALLEL_HORIZ, "Parallel"),
      Triple(8, GeoFigureType.PAIR_PARALLEL_VERT, "Parallel"),
      Triple(9, GeoFigureType.PAIR_INTERSECT_SLANT, "Intersecting"),
      Triple(10, GeoFigureType.PAIR_PERP_T, "Perpendicular"),
    )
    kItems.forEach { (num, fig, ans) ->
      list += MathProblem.GeometryIdentifyProblem(
        num, "Identify the pair of lines: parallel, intersecting, or perpendicular.", fig, kOpts, ans,
        "Perpendicular lines make a square 90° corner; intersecting lines cross at one point; parallel lines never meet.",
      )
    }
    // 11-14: Name the four lines in Diagram 1 (KL, MN, PQ, OR)
    val d1Valid = listOf("KL", "LK", "MN", "NM", "PQ", "QP", "OR", "RO")
    (11..14).forEach { num ->
      list += MathProblem.GeometryNameLinesProblem(
        itemNumber = num, diagramId = 1,
        prompt = "Name line #${num - 10} of the 4 lines shown in the figure.",
        validAnswers = d1Valid,
        hint = "Follow any straight line with arrows on both ends and enter the 2 capital letters of points on that line.",
      )
    }
    // 15-17: Name the three lines in Diagram 2 (AE, DB, CE)
    val d2Valid = listOf("AE", "EA", "DB", "BD", "CE", "EC", "CD", "DC", "DE", "ED")
    (15..17).forEach { num ->
      list += MathProblem.GeometryNameLinesProblem(
        itemNumber = num, diagramId = 2,
        prompt = "Name line #${num - 14} of the 3 lines shown in the figure.",
        validAnswers = d2Valid,
        hint = "Look at the horizontal, vertical, and slanted lines and enter the 2 capital letters of points on one line.",
      )
    }
    return list
  }

  fun getWorksheet23(): List<MathProblem> {
    val list = mutableListOf<MathProblem>()
    // Part I: 1-6 (ADEG rectangle, O intersection, P midpoint of DG)
    val part1 = listOf(
      Triple(1, "Compare AD and EG. Are they Equal (E) or Not Equal (NE)?", "E"),
      Triple(2, "Compare ED and OG. Are they Equal (E) or Not Equal (NE)?", "NE"),
      Triple(3, "Compare GP and AE. Are they Equal (E) or Not Equal (NE)?", "NE"),
      Triple(4, "Compare DO and OG. Are they Equal (E) or Not Equal (NE)?", "E"),
      Triple(5, "Compare PG and DA. Are they Equal (E) or Not Equal (NE)?", "NE"),
      Triple(6, "Compare GA and DE. Are they Equal (E) or Not Equal (NE)?", "NE"),
    )
    part1.forEach { (num, prompt, ans) ->
      list += MathProblem.CongruentSegmentsProblem(
        itemNumber = num, subType = CongruentSubType.ADEG_EQUAL_OR_NOT,
        prompt = prompt, options = listOf("E", "NE"), validAnswers = listOf(ans),
        hint = "Compare both segments on the rectangle: opposite sides are equal and diagonal halves from O are equal, while a side vs half-diagonal is usually not equal.",
      )
    }
    // Part II: 7-12 (Measure given segment using ruler, draw equal length)
    val lengths = listOf(5, 4, 5, 4, 3, 5)
    lengths.forEachIndexed { idx, units ->
      list += MathProblem.GeometryDrawProblem(
        itemNumber = 7 + idx,
        prompt = "Part II #${idx + 1}: Measure the given line segment ($units units) using the interactive ruler, then draw an equal-length segment!",
        guideHint = "Target length: $units ruler units",
        sampleSegmentUnits = units,
        hint = "Drag along the ruler grid to make a segment that is $units units long.",
      )
    }
    // Part III: 13-15
    list += MathProblem.CongruentSegmentsProblem(
      itemNumber = 13, subType = CongruentSubType.LNRQ_EQUAL_PAIRS,
      prompt = "Part III #1: In diamond LNRQ, pick a pair of equal line segments.",
      options = listOf("LN = NR", "LS = SR", "NS = SQ", "RQ = QL", "LN = LS"),
      validAnswers = listOf("LN = NR", "LS = SR", "NS = SQ", "RQ = QL"),
      hint = "Check outer sides of diamond LNRQ or halves of a diagonal bisected at S — avoid mixing a full side with a half-diagonal.",
    )
    list += MathProblem.CongruentSegmentsProblem(
      itemNumber = 14, subType = CongruentSubType.TUV_ALTITUDE_EQUAL,
      prompt = "Part III #2: Look at diamond LNRQ on the left and triangle TUV. Which line segment has the same length as TW?",
      options = listOf("NQ", "LR", "UV", "TU"),
      validAnswers = listOf("NQ", "LR"),
      hint = "Use the ruler to measure height TW and match it with a diagonal of diamond LNRQ.",
    )
    list += MathProblem.CongruentSegmentsProblem(
      itemNumber = 15, subType = CongruentSubType.TUV_EQUAL_SEGMENTS,
      prompt = "Part III #3: In triangle TUV, which line segments are equal?",
      options = listOf("TU = TV", "UW = WV", "TW = UV", "TU = UW"),
      validAnswers = listOf("TU = TV", "UW = WV"),
      hint = "Check the symmetric slanted sides of triangle TUV and how W divides base UV.",
    )
    // Part VI: 16-20 (KLNM rectangle with midpoints P, Q, A, B and center O)
    data class KlnmItem(val num: Int, val prompt: String, val valid: List<String>, val opts: List<String>)
    val klnm = listOf(
      KlnmItem(16, "In rectangle KLNM, KL = _____", listOf("MN", "NM"), listOf("MN", "LM", "KN", "PO")),
      KlnmItem(17, "In rectangle KLNM, LM = _____", listOf("KN", "NK"), listOf("KN", "KL", "MN", "BN")),
      KlnmItem(18, "In rectangle KLNM, KN = _____", listOf("LM", "ML"), listOf("LM", "KL", "MN", "AM")),
      KlnmItem(19, "In rectangle KLNM, PO = _____", listOf("QO", "OQ"), listOf("QO", "AO", "KO", "KL")),
      KlnmItem(20, "In rectangle KLNM, BN = _____", listOf("AM", "MA", "AK", "KA", "BL", "LB"), listOf("AM", "PO", "KL", "ON")),
    )
    klnm.forEach { (num, prompt, valid, opts) ->
      list += MathProblem.CongruentSegmentsProblem(
        itemNumber = num, subType = CongruentSubType.KLNM_CONGRUENT,
        prompt = prompt, options = opts, validAnswers = valid,
        hint = "Opposite sides and matching midpoint halves in a rectangle are congruent!",
      )
    }
    // Part V: 21-25 (Choose the pair of congruent line segments)
    val sets = listOf(
      Triple(21, listOf("AB & EF", "AB & CD", "CD & EF"), "AB & EF"),
      Triple(22, listOf("GH & JK", "GH & LM", "JK & LM"), "GH & JK"),
      Triple(23, listOf("QR & ST", "NP & QR", "NP & ST"), "QR & ST"),
      Triple(24, listOf("UV & YZ", "UV & WX", "WX & YZ"), "UV & YZ"),
      Triple(25, listOf("FG & HI", "DE & FG", "DE & HI"), "FG & HI"),
    )
    sets.forEachIndexed { idx, (num, opts, ans) ->
      list += MathProblem.CongruentSegmentsProblem(
        itemNumber = num, subType = CongruentSubType.COMPARE_THREE_SEGMENTS,
        prompt = "Part V #${idx + 1}: Which pair of line segments are congruent (same length)?",
        options = opts, validAnswers = listOf(ans), setIndex = idx,
        hint = "Compare the lengths visually — look for the two bars that match!",
      )
    }
    return list
  }
}

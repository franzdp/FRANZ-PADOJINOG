package com.example.data

import com.example.data.g6.Grade6DivisibilityAndPmdasData
import com.example.data.g6.Grade6FractionsDivData
import com.example.data.g6.Grade6FractionsMultData
import com.example.data.g6.Grade6GcfLcmData
import com.example.data.g6.Grade6MeasureTempData
import com.example.data.g6.Grade6PolygonsTimeData
import com.example.model.MathProblem

object Grade6ProblemsRepository {
  fun getProblemsForWorksheet(worksheetId: Int): List<MathProblem> = when (worksheetId) {
    1 -> Grade6DivisibilityAndPmdasData.ws1
    2 -> Grade6DivisibilityAndPmdasData.ws2
    3 -> Grade6DivisibilityAndPmdasData.ws3
    4 -> Grade6DivisibilityAndPmdasData.ws4
    5 -> Grade6DivisibilityAndPmdasData.ws5
    6 -> Grade6DivisibilityAndPmdasData.ws6
    7 -> Grade6DivisibilityAndPmdasData.ws7
    8 -> Grade6DivisibilityAndPmdasData.ws8
    9 -> Grade6GcfLcmData.ws9
    10 -> Grade6GcfLcmData.ws10
    11 -> Grade6GcfLcmData.ws11
    12 -> Grade6GcfLcmData.ws12
    13 -> Grade6GcfLcmData.ws13
    14 -> Grade6FractionsMultData.ws14
    15 -> Grade6FractionsMultData.ws15
    16 -> Grade6FractionsMultData.ws16
    17 -> Grade6FractionsMultData.ws17
    18 -> Grade6FractionsMultData.ws18
    19 -> Grade6FractionsDivData.ws19
    20 -> Grade6FractionsDivData.ws20
    21 -> Grade6FractionsDivData.ws21
    22 -> Grade6FractionsDivData.ws22
    23 -> Grade6FractionsDivData.ws23
    24 -> Grade6FractionsDivData.ws24
    25 -> Grade6PolygonsTimeData.ws25
    26 -> Grade6PolygonsTimeData.ws26
    27 -> Grade6PolygonsTimeData.ws27
    28 -> Grade6PolygonsTimeData.ws28
    29 -> Grade6PolygonsTimeData.ws29
    30 -> Grade6MeasureTempData.ws30
    31 -> Grade6MeasureTempData.ws31
    32 -> Grade6MeasureTempData.ws32
    33 -> Grade6MeasureTempData.ws33
    34 -> Grade6MeasureTempData.ws34
    35 -> Grade6MeasureTempData.ws35
    else -> emptyList()
  }
}

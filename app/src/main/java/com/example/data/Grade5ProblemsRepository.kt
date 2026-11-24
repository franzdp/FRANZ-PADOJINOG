package com.example.data

import com.example.data.g5.Grade5AnglesData
import com.example.data.g5.Grade5DivisionData
import com.example.data.g5.Grade5MultiplyData
import com.example.data.g5.Grade5NumbersWordsRoundingData
import com.example.data.g5.Grade5PlaceValueData
import com.example.data.g5.Grade5WordProblemsAndOpsData
import com.example.model.MathProblem

object Grade5ProblemsRepository {
  fun getProblemsForWorksheet(worksheetId: Int): List<MathProblem> = when (worksheetId) {
    1 -> Grade5PlaceValueData.ws1
    2 -> Grade5PlaceValueData.ws2
    3 -> Grade5PlaceValueData.ws3
    4 -> Grade5PlaceValueData.ws4
    5 -> Grade5PlaceValueData.ws5
    6 -> Grade5NumbersWordsRoundingData.ws6
    7 -> Grade5NumbersWordsRoundingData.ws7
    8 -> Grade5NumbersWordsRoundingData.ws8
    9 -> Grade5NumbersWordsRoundingData.ws9
    10 -> Grade5NumbersWordsRoundingData.ws10
    11 -> Grade5NumbersWordsRoundingData.ws11
    12 -> Grade5MultiplyData.ws12
    13 -> Grade5MultiplyData.ws13
    14 -> Grade5MultiplyData.ws14
    15 -> Grade5MultiplyData.ws15
    16 -> Grade5MultiplyData.ws16
    17 -> Grade5MultiplyData.ws17
    18 -> Grade5DivisionData.ws18
    19 -> Grade5DivisionData.ws19
    20 -> Grade5DivisionData.ws20
    21 -> Grade5DivisionData.ws21
    22 -> Grade5DivisionData.ws22
    23 -> Grade5DivisionData.ws23
    24 -> Grade5DivisionData.ws24
    25 -> Grade5DivisionData.ws25
    26 -> Grade5WordProblemsAndOpsData.ws26
    27 -> Grade5WordProblemsAndOpsData.ws27
    28 -> Grade5WordProblemsAndOpsData.ws28
    29 -> Grade5WordProblemsAndOpsData.ws29
    30 -> Grade5AnglesData.ws30
    31 -> Grade5AnglesData.ws31
    32 -> Grade5AnglesData.ws32
    else -> emptyList()
  }
}

package com.example.data

import com.example.model.AralGrade
import com.example.model.MathProblem

object WorksheetProblemsRepository {
  fun getProblemsForWorksheet(worksheetId: Int): List<MathProblem> = when (worksheetId) {
    1 -> FractionsAndPatternsData.getWorksheet1()
    2 -> FractionsAndPatternsData.getWorksheet2()
    3 -> FractionsAndPatternsData.getWorksheet3()
    4 -> FractionsAndPatternsData.getWorksheet4()
    5 -> FractionsAndPatternsData.getWorksheet5()
    6 -> FractionsAndPatternsData.getWorksheet6()
    7 -> ArithmeticData.getWorksheet7()
    8 -> ArithmeticData.getWorksheet8()
    9 -> ArithmeticData.getWorksheet9()
    10 -> ArithmeticData.getWorksheet10()
    11 -> ArithmeticData.getWorksheet11()
    12 -> ArithmeticData.getWorksheet12()
    13 -> ArithmeticData.getWorksheet13()
    14 -> ArithmeticData.getWorksheet14()
    15 -> DivisionData.getWorksheet15()
    16 -> DivisionData.getWorksheet16()
    17 -> DivisionData.getWorksheet17()
    18 -> DivisionData.getWorksheet18()
    19 -> DivisionData.getWorksheet19()
    20 -> DivisionData.getWorksheet20()
    21 -> GeometryData.getWorksheet21()
    22 -> GeometryData.getWorksheet22()
    23 -> GeometryData.getWorksheet23()
    24 -> NumberDiscsAndPlaceValueData.getWorksheet24()
    25 -> NumberDiscsAndPlaceValueData.getWorksheet25()
    26 -> NumberDiscsAndPlaceValueData.getWorksheet26()
    27 -> NumberDiscsAndPlaceValueData.getWorksheet27()
    28 -> NumberDiscsAndPlaceValueData.getWorksheet28()
    29 -> NumberWordsAndOrderingData.getWorksheet29()
    30 -> NumberWordsAndOrderingData.getWorksheet30()
    31 -> NumberWordsAndOrderingData.getWorksheet31()
    32 -> NumberWordsAndOrderingData.getWorksheet32()
    33 -> NumberWordsAndOrderingData.getWorksheet33()
    34 -> NumberWordsAndOrderingData.getWorksheet34()
    else -> emptyList()
  }

  fun getProblemsForWorksheet(grade: AralGrade, worksheetId: Int): List<MathProblem> = when (grade) {
    AralGrade.GRADE_2 -> Grade2ProblemsRepository.getProblemsForWorksheet(worksheetId)
    AralGrade.GRADE_3 -> Grade3ProblemsRepository.getProblemsForWorksheet(worksheetId)
    AralGrade.GRADE_4 -> getProblemsForWorksheet(worksheetId)
    AralGrade.GRADE_5 -> Grade5ProblemsRepository.getProblemsForWorksheet(worksheetId)
    AralGrade.GRADE_6 -> Grade6ProblemsRepository.getProblemsForWorksheet(worksheetId)
  }
}

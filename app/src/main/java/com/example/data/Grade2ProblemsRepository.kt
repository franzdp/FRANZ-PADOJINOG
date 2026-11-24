package com.example.data

import com.example.data.g2.*
import com.example.model.MathProblem

object Grade2ProblemsRepository {
  fun getProblemsForWorksheet(worksheetId: Int): List<MathProblem> = when (worksheetId) {
    1 -> G2CountAndWordsData.getWorksheet1()
    2 -> G2CountAndWordsData.getWorksheet2()
    3 -> G2CountAndWordsData.getWorksheet3()
    4 -> G2CountAndWordsData.getWorksheet4()
    5 -> G2CountAndWordsData.getWorksheet5()
    6 -> G2CountAndWordsData.getWorksheet6()
    7 -> G2CompareOrderData.getWorksheet7()
    8 -> G2CompareOrderData.getWorksheet8()
    9 -> G2CompareOrderData.getWorksheet9()
    10 -> G2CompareOrderData.getWorksheet10()
    11 -> G2CompareOrderData.getWorksheet11()
    12 -> G2CompareOrderData.getWorksheet12()
    13 -> G2CompareOrderData.getWorksheet13()
    14 -> G2FractionsAndAddData.getWorksheet14()
    15 -> G2FractionsAndAddData.getWorksheet15()
    16 -> G2FractionsAndAddData.getWorksheet16()
    17 -> G2FractionsAndAddData.getWorksheet17()
    18 -> G2FractionsAndAddData.getWorksheet18()
    19 -> G2FractionsAndAddData.getWorksheet19()
    20 -> G2FractionsAndAddData.getWorksheet20()
    21 -> G2FractionsAndAddData.getWorksheet21()
    22 -> G2FractionsAndAddData.getWorksheet22()
    23 -> G2FractionsAndAddData.getWorksheet23()
    24 -> G2SubtractShapesCalendarData.getWorksheet24()
    25 -> G2SubtractShapesCalendarData.getWorksheet25()
    26 -> G2SubtractShapesCalendarData.getWorksheet26()
    27 -> G2SubtractShapesCalendarData.getWorksheet27()
    28 -> G2SubtractShapesCalendarData.getWorksheet28()
    29 -> G2SubtractShapesCalendarData.getWorksheet29()
    30 -> G2SubtractShapesCalendarData.getWorksheet30()
    31 -> G2SubtractShapesCalendarData.getWorksheet31()
    32 -> G2SubtractShapesCalendarData.getWorksheet32()
    33 -> G2SubtractShapesCalendarData.getWorksheet33()
    34 -> G2SubtractShapesCalendarData.getWorksheet34()
    35 -> G2SubtractShapesCalendarData.getWorksheet35()
    36 -> G2SubtractShapesCalendarData.getWorksheet36()
    else -> emptyList()
  }
}

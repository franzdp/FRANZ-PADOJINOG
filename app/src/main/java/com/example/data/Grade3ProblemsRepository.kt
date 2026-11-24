package com.example.data

import com.example.data.g3.G3AddSubData
import com.example.data.g3.G3GeometryPatternsData
import com.example.data.g3.G3MultDivData
import com.example.data.g3.G3NumbersAndOrderData
import com.example.data.g3.G3PatternsSkipFractionsData
import com.example.model.MathProblem

object Grade3ProblemsRepository {
  fun getProblemsForWorksheet(worksheetId: Int): List<MathProblem> = when (worksheetId) {
    1 -> G3NumbersAndOrderData.getWorksheet1()
    2 -> G3NumbersAndOrderData.getWorksheet2()
    3 -> G3NumbersAndOrderData.getWorksheet3()
    4 -> G3NumbersAndOrderData.getWorksheet4()
    5 -> G3PatternsSkipFractionsData.getWorksheet5()
    6 -> G3PatternsSkipFractionsData.getWorksheet6()
    7 -> G3PatternsSkipFractionsData.getWorksheet7()
    8 -> G3PatternsSkipFractionsData.getWorksheet8()
    9 -> G3AddSubData.getWorksheet9()
    10 -> G3AddSubData.getWorksheet10()
    11 -> G3AddSubData.getWorksheet11()
    12 -> G3AddSubData.getWorksheet12()
    13 -> G3AddSubData.getWorksheet13()
    14 -> G3MultDivData.getWorksheet14()
    15 -> G3MultDivData.getWorksheet15()
    16 -> G3MultDivData.getWorksheet16()
    17 -> G3MultDivData.getWorksheet17()
    18 -> G3MultDivData.getWorksheet18()
    19 -> G3MultDivData.getWorksheet19()
    20 -> G3GeometryPatternsData.getWorksheet20()
    21 -> G3GeometryPatternsData.getWorksheet21()
    22 -> G3GeometryPatternsData.getWorksheet22()
    23 -> G3GeometryPatternsData.getWorksheet23()
    24 -> G3GeometryPatternsData.getWorksheet24()
    else -> emptyList()
  }
}

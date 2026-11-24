package com.example.viewmodel

import com.example.model.MathProblem

data class AnswerCheckResult(
  val isCorrect: Boolean,
  val message: String,
  val expectedDisplay: String,
)

object AnswerChecker {

  fun normalizeWords(raw: String): String =
    raw.trim()
      .lowercase()
      .replace("-", " ")
      .replace(",", " ")
      .replace(Regex("\\s+"), " ")
      .trim()

  fun normalizeDigits(raw: String): String =
    raw.trim()
      .replace(",", "")
      .replace("₱", "")
      .replace(" ", "")
      .replace(".00", "")

  fun checkFractionInput(raw: String, expectedNum: Int, expectedDen: Int): Boolean {
    val clean = raw.trim().replace(" ", "")
    val parts = clean.split("/")
    if (parts.size != 2) return false
    val n = parts[0].toIntOrNull() ?: return false
    val d = parts[1].toIntOrNull() ?: return false
    return n == expectedNum && d == expectedDen
  }

  fun checkPatternAnswer(problem: MathProblem.PatternProblem, inputs: List<String>): AnswerCheckResult {
    val expected = problem.expectedAnswers
    val ok = inputs.size == expected.size && inputs.zip(expected).all { (u, e) ->
      normalizeDigits(u).equals(normalizeDigits(e), ignoreCase = true) ||
        u.trim().equals(e.trim(), ignoreCase = true)
    }
    return AnswerCheckResult(
      isCorrect = ok,
      message = if (ok) "Pattern solved! Great detective work!" else "Check the pattern rule and try again.",
      expectedDisplay = expected.joinToString(", "),
    )
  }

  fun checkDivisionInput(
    problem: MathProblem.DivisionProblem,
    quotientInput: String,
    remainderInput: String,
  ): AnswerCheckResult {
    val q = normalizeDigits(quotientInput).toIntOrNull()
    val r = if (remainderInput.isBlank()) 0 else normalizeDigits(remainderInput).toIntOrNull()
    val ok = q == problem.quotient && r == problem.remainder
    val expectedStr = if (problem.remainder == 0) "${problem.quotient}" else "${problem.quotient} R ${problem.remainder}"
    return AnswerCheckResult(
      isCorrect = ok,
      message = if (ok) "Spot on! Awesome dividing!" else "Try checking your multiplication & subtraction!",
      expectedDisplay = expectedStr,
    )
  }

  fun checkNumberWords(problem: MathProblem.NumberWordsProblem, rawInput: String): AnswerCheckResult {
    val ok = if (problem.toWords) {
      normalizeWords(rawInput) == normalizeWords(problem.words)
    } else {
      normalizeDigits(rawInput).toIntOrNull() == problem.number
    }
    val expected = if (problem.toWords) problem.words else "%,d".format(problem.number)
    return AnswerCheckResult(
      isCorrect = ok,
      message = if (ok) "Magaling! Perfect number form!" else "Check your spelling or place values and try again.",
      expectedDisplay = expected,
    )
  }

  fun checkFractionWords(problem: MathProblem.FractionWordsProblem, rawInput: String): AnswerCheckResult {
    val ok = if (problem.toWords) {
      val normU = normalizeWords(rawInput)
      val normE = normalizeWords(problem.words)
      normU == normE || (problem.num == 1 && problem.den == 2 && normU == "a half")
    } else {
      checkFractionInput(rawInput, problem.num, problem.den)
    }
    val expected = if (problem.toWords) problem.words else "${problem.num}/${problem.den}"
    return AnswerCheckResult(
      isCorrect = ok,
      message = if (ok) "Awesome fraction skills!" else "Remember: numerator is cardinal, denominator is ordinal.",
      expectedDisplay = expected,
    )
  }
}

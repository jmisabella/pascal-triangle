package models.modules

import models.classes.{ Approximation, Approximations, Combination }
import models.behaviors.ExponentialApproximation
import java.text.NumberFormat

case object Combo extends ExponentialApproximation {
  def formattedResultJson(n: Int, k: Int, maxIntegerLength: Int, scale: Int, maxPascalSize: Int = 500): String = {
    val (combination, solution): (Combination, Either[String, Seq[Seq[BigInt]]]) = n * k match {
      case p if (p <= maxPascalSize) => combinationByPascalsTriangle.combinationCountAndSolution(n, k) 
      case _ => combinationByFactorial.combinationCountAndSolution(n, k) 
    } 
    solution match {
      case Left(formula) => {
        val approx: Approximations = format(Seq(combination.combinations), maxIntegerLength, scale)//.approximations.head
        val msg: String = solution match {
          case Left(s) => s
          case Right(_) => ""
        }
        val json = s"""{ "msg": "$msg", "rows": [ $approx ] }"""
        json
      }
      case Right(triangle) => {
        val approximations: Seq[Approximations] = triangle.map(x => format(x, maxIntegerLength, scale)).toList
        val approximationsLastRow: Approximations = approximations.reverse.head
        val combinations = NumberFormat.getInstance().format(approximationsLastRow.approximations(k).actual)
        val solution1 = s"""${n}C${k} combinations is row ${n+1} column ${k+1} of pascal's triangle, ${combinations}""" 
        val solution2 = s"""When selecting $k unique items out of $n total, there are $combinations unique combinations"""
        val msg = Seq(solution1, solution2).mkString("<br />")   
        val json = approximations.mkString("""{ "rows": [""", ", ", "]") + s""", "msg": "$msg"}"""
        json
      }
    }
  }
}


package models.modules

import models.classes.{ Approximation, Approximations, Combination }
import models.behaviors.ExponentialApproximation

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
        val combinations = approximationsLastRow.approximations(k).approximation
        val msg = s""" ${n}C${k} combinations is row ${n+1} column ${k+1} of pascal's triangle, ${combinations}""" 
        val json = approximations.mkString("""{ "rows": [""", ", ", "]") + s""", "msg": "$msg"}"""
        json
      }
    }
  }
}


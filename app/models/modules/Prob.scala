package models.modules

import models.classes.{ Approximation, Approximations, Probability }
import models.behaviors.{ BooleanProbability, ExponentialApproximation }
import models.behaviors.pascal.PascalsTriangleByElementWiseAddition

case object Prob extends BooleanProbability with ExponentialApproximation {
  override type _PascalsTriangle = PascalsTriangleByElementWiseAddition
  override val pascal = Triangle

  def formattedResultJson(n: Int, k: Int, maxIntegerLength: Int, scale: Int): String = {
    val (probable, triangle): (Probability, Seq[Seq[BigInt]]) = probabilityAndTriangle(n, k)
    val approximations: Seq[Approximations] = triangle.map(x => format(x, maxIntegerLength, scale)).toList
    val approximationsLastRow: Approximations = approximations.reverse.head
    val sum: BigInt = approximationsLastRow.map(_.actual).foldLeft(BigInt(0))(_ + _)
    val solutionPart1 = approximationsLastRow.map(_.approximation).mkString(" + ") + " = " + sum 
    val solutionPart2 = s"Denominator is $sum (sum of row ${n+1})"
    val solutionPart3 = s"Numerator is ${approximationsLastRow.approximations(k).actual} (column ${k+1})"
    val solutionPart4 = s"${approximationsLastRow.approximations(k).actual} / $sum = ${probable.probability}%"
    val solutionPart5 = probable.toString()
    val msg = Seq(solutionPart1, solutionPart2, solutionPart3, solutionPart4, solutionPart5).mkString("<br />") 
    val json = approximations.mkString("""{ "rows": [""", ", ", "]") + s""", "msg": "$msg"}"""
    json
  }
}


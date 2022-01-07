package models.modules

import models.classes.{ Approximation, Approximations, Probability }
import models.behaviors.{ BooleanProbability, ExponentialApproximation }
import models.behaviors.pascal.PascalsTriangleByElementWiseAddition
import java.text.NumberFormat

case object Prob extends BooleanProbability with ExponentialApproximation {
  override type _PascalsTriangle = PascalsTriangleByElementWiseAddition
  override val pascal = Triangle

  def formattedResultJson(n: Int, k: Int, maxIntegerLength: Int, scale: Int): String = {
    val (probable, triangle): (Probability, Seq[Seq[BigInt]]) = probabilityAndTriangle(n, k)
    val approximations: Seq[Approximations] = triangle.map(x => format(x, maxIntegerLength, scale)).toList
    val approximationsLastRow: Approximations = approximations.reverse.head
    val sum = NumberFormat.getIntegerInstance().format(approximationsLastRow.map(_.actual).foldLeft(BigInt(0))(_ + _))
    val numerator = NumberFormat.getIntegerInstance().format(approximationsLastRow.approximations(k).actual)
    val solutionPart1 = "( " + approximationsLastRow.map(_.approximation).mkString(" + ") + " = " + sum + " )"
    val solutionPart2 = s"Denominator is $sum (sum of row ${n+1})"
    val solutionPart3 = s"Numerator is $numerator (column ${k+1})"
    val solutionPart4 = s"$numerator / $sum = ${probable.probability}%"
    val solutionPart5 = probable.toString()
    val msg = Seq(solutionPart1, solutionPart2, solutionPart3, solutionPart4, solutionPart5).mkString("<br />") 
    val json = approximations.mkString("""{ "rows": [""", ", ", "]") + s""", "msg": "$msg"}"""
    json
  }
}


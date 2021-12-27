package models.behaviors

import _root_.models.behaviors.pascal.PascalsTriangle
import _root_.models.classes.BooleanAlgebraProbability
import java.lang.Math.{ pow, floor }

trait BooleanProbability {
  type _PascalsTriangle <: PascalsTriangle 
  val pascal: _PascalsTriangle

  // TODO: test
  def round(value: Double, numberOfDecimals: Int): Double = {
    val placementMultiplier = pow(10, numberOfDecimals)
    floor(value * placementMultiplier + 0.5) / placementMultiplier
  }

  def probability(n: Int, k: Int): BooleanAlgebraProbability = {
    val triangle = pascal.pascalTriangle(n + 1)
    val row: Seq[BigInt] = triangle(n)
    val denominator: BigInt = row.foldLeft(BigInt(0))(_ + _)
    val numerator: BigInt = row(k)
    val probability = (numerator.toDouble / denominator.toDouble) * 100.0
    BooleanAlgebraProbability(n, k, round(probability, 2)) // rounded to nearest hundredth
  }

}
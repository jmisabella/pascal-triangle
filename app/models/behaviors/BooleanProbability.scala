package models.behaviors

import _root_.models.behaviors.pascal.PascalsTriangle
import _root_.models.classes.Probability
import java.lang.Math.{ pow, floor }

trait BooleanProbability {
  type _PascalsTriangle <: PascalsTriangle 
  val pascal: _PascalsTriangle

  // scale is number of decimal digits to preserve when rounding
  def round(value: Double, scale: Int): Double = {
    val placementMultiplier = pow(10, scale)
    floor(value * placementMultiplier + 0.5) / placementMultiplier
  }

  def probability(n: Int, k: Int): Probability = {
    val triangle = pascal.pascalTriangle(n + 1)
    val row: Seq[BigInt] = triangle(n)
    val denominator: BigInt = row.foldLeft(BigInt(0))(_ + _)
    val numerator: BigInt = row(k)
    val probability = (numerator.toDouble / denominator.toDouble) * 100.0
    Probability(n, k, round(probability, 2)) // rounded to nearest hundredth
  }

  // TODO: test
  def probabilityAndTriangle(n: Int, k: Int): (Probability, Seq[Seq[BigInt]]) = {
    val triangle = pascal.pascalTriangle(n + 1)
    val row: Seq[BigInt] = triangle(n)
    val denominator: BigInt = row.foldLeft(BigInt(0))(_ + _)
    val numerator: BigInt = row(k)
    val probability = (numerator.toDouble / denominator.toDouble) * 100.0
    (Probability(n, k, round(probability, 2)), triangle) // rounded to nearest hundredth
  }
}
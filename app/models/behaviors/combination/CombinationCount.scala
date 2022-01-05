package models.behaviors.combination

import models.classes.Combination

trait CombinationCount {
  def combinationCount(n: Int, k: Int): Combination
  // solution is either Left: mathematical formula, or Right: pascal's triangle
  def combinationCountAndSolution(n: Int, k: Int): (Combination, Either[String, Seq[Seq[BigInt]]])
}
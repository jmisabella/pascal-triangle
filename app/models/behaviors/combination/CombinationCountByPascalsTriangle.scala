package models.behaviors.combination

import models.behaviors.pascal.PascalsTriangle
import models.classes.Combination

trait CombinationCountByPascalsTriangle extends CombinationCount {
  type _PascalsTriangle <: PascalsTriangle 
  val pascal: _PascalsTriangle

  override def combinationCount(n: Int, k: Int): Combination = {
    val triangle = pascal.pascalTriangle(n + 1)
    val row: Seq[BigInt] = triangle(n)
    val count: BigInt = row(k)
    Combination(n, k, count)
  }

  // TODO: test
  override def combinationCountAndSolution(n: Int, k: Int): (Combination, Either[String, Seq[Seq[BigInt]]]) = {
    val triangle = pascal.pascalTriangle(n + 1)
    val row: Seq[BigInt] = triangle(n)
    val count: BigInt = row(k)
    (Combination(n, k, count), Right(triangle))
  }

}
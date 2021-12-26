package models.behaviors.combination

import models.behaviors.Factorial
import models.classes.Combination

trait CombinationCountByFactorial extends CombinationCount {
  type _Factorial <: Factorial 
  val factorial: _Factorial

  //       n!  
  //    --------
  //    k!(n-k)!
  override def combinationCount(n: Int, k: Int): Combination = {
    val count = factorial.factorial(n) / (factorial.factorial(k) * factorial.factorial(n - k))
    Combination(n, k, count)
  }
}
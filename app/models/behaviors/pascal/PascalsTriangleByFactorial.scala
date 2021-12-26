package models.behaviors.pascal

import models.behaviors.Factorial

trait PascalsTriangleByFactorial extends PascalsTriangle {
  type _Factorial <: Factorial
  val factorial: _Factorial

  //       n!  
  //    --------
  //    (n-r)!r!
  override def pascalTriangle(rows: Int): Seq[Seq[BigInt]] = {
    for (i <- 0 until rows) yield {
      for (j <- 0 to i) yield {
        factorial.factorial(i) / (factorial.factorial(i - j) * factorial.factorial(j))
      }
    }
  }

}
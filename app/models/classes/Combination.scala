package models.classes

import java.text.NumberFormat

case class Combination(n: Int, k: Int, combinations: BigInt) {
  require(
    k <= n, 
    s"Combination is n things taken k at a time without repetition: nCk. k cannot exceed n, however k is [$k] and n is [$n]"
  )

  // nCk (e.g. 6C2)
  override def toString(): String = {
    val f = NumberFormat.getIntegerInstance().format: BigInt => String // function for adding commas
    s"${f(n)}C${f(k)} = ${f(combinations)}"
  }
}
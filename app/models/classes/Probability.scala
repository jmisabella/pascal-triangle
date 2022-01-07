package models.classes

case class Probability(n: Int, k: Int, probability: Double) {
  require(
    k <= n, 
    s"Probability is the probable percentage of 1 from [0, 1] occuring k times out of n total tries; k cannot exceed n, however k is [$k] and n is [$n]"
  )

  override def toString(): String = (n, k) match {
    case (1, 1) => s"Out of $n try selecting from [0, 1], probability of 1 occurring exactly $k time is $probability%"
    case (1, _) => s"Out of $n try selecting from [0, 1], probability of 1 occurring exactly $k times is $probability%"
    case (_, 1) => s"Out of $n tries selecting from [0, 1], probability of 1 occurring exactly $k time is $probability%"
    case (_, _) => s"Out of $n tries selecting from [0, 1], probability of 1 occurring exactly $k times is $probability%"
  }
}
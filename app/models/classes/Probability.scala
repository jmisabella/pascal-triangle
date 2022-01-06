package models.classes

case class Probability(n: Int, k: Int, probability: Double) {
  require(
    k <= n, 
    s"Probability is the probable percentage of 1 from [0, 1] occuring k times out of n total tries; k cannot exceed n, however k is [$k] and n is [$n]"
  )

  override def toString(): String = s"Out of $n tries selecting from [0, 1], probability of 1 occurring $k times is $probability%"
}
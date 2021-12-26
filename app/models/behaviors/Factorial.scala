package models.behaviors

import scala.annotation.tailrec

trait Factorial {
  // n! = n × (n−1)!
  @tailrec
  final def factorial(value: BigInt, acc: BigInt = BigInt(1)): BigInt = value match {
    // TODO: what is the best way for us to prevent negative arguments?
    case i if (i == BigInt(0)) => acc
    case i => factorial(i - 1, acc * i)
  }
}

package models.behaviors

import scala.annotation.tailrec

trait Factorial {
  // n! = n × (n−1)!
  @tailrec
  final def factorial(value: BigInt, acc: BigInt = BigInt(1)): BigInt = value match {
    case i if (i < 0) => throw new IllegalArgumentException(s"Cannot determine factorial of a negative number: [$i]")
    case i if (i == BigInt(0)) => acc
    case i => factorial(i - 1, acc * i)
  }
}

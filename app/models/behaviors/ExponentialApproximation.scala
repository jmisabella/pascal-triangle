package models.behaviors

import _root_.models.classes.{ Approximation, Approximations }

import java.text.DecimalFormat
import java.math.RoundingMode

trait ExponentialApproximation {
  
  def format(x: BigInt, maxIntegerLength: Int, scale: Int): Approximation = x.toString().length() match {
    case n if (n <= maxIntegerLength) => Approximation(x, x.toString())
    case _ => { // only format when digits exceed max length
      val formatter = new DecimalFormat("0.0E0")
      formatter.setRoundingMode(RoundingMode.HALF_UP)
      formatter.setMinimumFractionDigits(scale)
      Approximation(x, formatter.format(BigDecimal(x)))
    } 
  }

  // def format(xs: Seq[BigInt], maxIntegerLength: Int, scale: Int): Seq[Approximation] = xs.map(format(_, maxIntegerLength, scale))
  def format(xs: Seq[BigInt], maxIntegerLength: Int, scale: Int): Approximations = Approximations(xs.map(format(_, maxIntegerLength, scale)))
}
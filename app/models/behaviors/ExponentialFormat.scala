package models.behaviors

import java.text.DecimalFormat
import java.math.RoundingMode

trait ExponentialFormat {
  
  def format(x: BigInt, maxIntegerLength: Int, scale: Int): String = x.toString().length() match {
    case n if (n <= maxIntegerLength) => x.toString() 
    case _ => { // only format when digits exceed max length
      val formatter = new DecimalFormat("0.0E0")
      formatter.setRoundingMode(RoundingMode.HALF_UP)
      formatter.setMinimumFractionDigits(scale)
      formatter.format(BigDecimal(x))
    } 
  }

  def format(xs: Seq[BigInt], maxIntegerLength: Int, scale: Int): Seq[String] = xs.map(format(_, maxIntegerLength, scale))
}
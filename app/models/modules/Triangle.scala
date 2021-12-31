package models.modules

import models.behaviors.pascal.PascalsTriangleByElementWiseAddition
import models.behaviors.{ ElementWiseAddition, ExponentialApproximation }
import models.classes.Approximations

case object _addition extends ElementWiseAddition

case object Triangle extends PascalsTriangleByElementWiseAddition with ExponentialApproximation {
  override type _ElementWiseAddition = ElementWiseAddition
  override val addition = _addition

  def formattedTriangleJson(rows: Int, maxIntegerLength: Int, scale: Int): String = {
    val values: Seq[Seq[BigInt]] = Triangle.pascalTriangle(rows)
    val approximations: Seq[Approximations] = values.map(x => Triangle.format(x, maxIntegerLength, scale)).toList
    val json = approximations.mkString("""{ "rows": [""", ", ", """]}""")
    json
  }
}

package models.modules

import models.behaviors.pascal.PascalsTriangleByElementWiseAddition
import models.behaviors.{ ElementWiseAddition, ExponentialApproximation }

case object _addition extends ElementWiseAddition

case object Triangle extends PascalsTriangleByElementWiseAddition with ExponentialApproximation {
  override type _ElementWiseAddition = ElementWiseAddition
  override val addition = _addition 
}

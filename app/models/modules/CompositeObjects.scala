package models.modules

import models.behaviors.{ Factorial, ElementWiseAddition }
import models.behaviors.pascal.PascalsTriangleByElementWiseAddition
import models.behaviors.combination.{ CombinationCountByFactorial, CombinationCountByPascalsTriangle }
import models.classes.{ Approximation, Approximations, Combination }
import models.behaviors.ExponentialApproximation

private case object _factorial extends Factorial

private case object _addition extends ElementWiseAddition
// private case object _a extends ElementWiseAddition
private case object _pascalByElementWiseAddition extends PascalsTriangleByElementWiseAddition {
  override type _ElementWiseAddition = ElementWiseAddition
  override val addition = _addition
}

private case object combinationByFactorial extends CombinationCountByFactorial {
  override type _Factorial = Factorial
  override val factorial = _factorial
}

private case object combinationByPascalsTriangle extends CombinationCountByPascalsTriangle {
  override type _PascalsTriangle = PascalsTriangleByElementWiseAddition
  override val pascal = _pascalByElementWiseAddition
}

package models.behaviors

import _root_.models.behaviors.pascal.PascalsTriangleByElementWiseAddition
import _root_.models.behaviors.BooleanProbability

import org.scalatest.flatspec.AnyFlatSpec

class BooleanProbabilitySpec extends AnyFlatSpec {
  private case object _addition extends ElementWiseAddition
  private case object _pascalByElementWiseAddition extends PascalsTriangleByElementWiseAddition {
    override type _ElementWiseAddition = ElementWiseAddition
    override val addition = _addition 
  }

  private case object probability extends BooleanProbability {
    override type _PascalsTriangle = PascalsTriangleByElementWiseAddition
    override val pascal = _pascalByElementWiseAddition
  }

  "BooleanProbability behavior" should "predict 50% probability of 1 out of [0, 1] occurring [0] times out of 1 try" in {
    val probable = probability.probability(1, 0)
    val expected = 50.0
    val actual = probable.probability
    assert(expected == actual, s"Expected [$expected], actual [$actual]")
  }

  it should "predict 50% probability of 1 out of [0, 1] occurring [1] time out of 1 try" in {
    val probable = probability.probability(1, 1)
    val expected = 50.0
    val actual = probable.probability
    assert(expected == actual, s"Expected [$expected], actual [$actual]")
  } 

  it should "predict 50% probability of 1 out of [0, 1] occurring [1] time out of 2 tries" in {
    val probable = probability.probability(2, 1)
    val expected = 50.0
    val actual = probable.probability
    assert(expected == actual, s"Expected [$expected], actual [$actual]")
  } 

  it should "predict 25% probability of 1 out of [0, 1] occurring [0] times out of 2 tries" in {
    val probable = probability.probability(2, 0)
    val expected = 25.0
    val actual = probable.probability
    assert(expected == actual, s"Expected [$expected], actual [$actual]")
  } 

  it should "predict 25% probability of 1 out of [0, 1] occurring [2] times out of 2 tries" in {
    val probable = probability.probability(2, 2)
    val expected = 25.0
    val actual = probable.probability
    assert(expected == actual, s"Expected [$expected], actual [$actual]")
  } 

  it should "predict 12.5% probability of 1 out of [0, 1] occurring [0] times out of 3 tries" in {
    val probable = probability.probability(3, 0)
    val expected = 12.5
    val actual = probable.probability
    assert(expected == actual, s"Expected [$expected], actual [$actual]")
  }
  
  it should "predict 37.5% probability of 1 out of [0, 1] occurring [1] time out of 3 tries" in {
    val probable = probability.probability(3, 1)
    val expected = 37.5
    val actual = probable.probability
    assert(expected == actual, s"Expected [$expected], actual [$actual]")
  }
  
  it should "predict 37.5% probability of 1 out of [0, 1] occurring [2] times out of 3 tries" in {
    val probable = probability.probability(3, 2)
    val expected = 37.5
    val actual = probable.probability
    assert(expected == actual, s"Expected [$expected], actual [$actual]")
  }
  
  it should "predict 12.5% probability of 1 out of [0, 1] occurring [3] times out of 3 tries" in {
    val probable = probability.probability(3, 3)
    val expected = 12.5
    val actual = probable.probability
    assert(expected == actual, s"Expected [$expected], actual [$actual]")
  }

}
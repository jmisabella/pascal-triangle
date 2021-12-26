package models.behaviors.combination

import models.behaviors.{ Factorial, ElementWiseAddition }
import models.behaviors.pascal.PascalsTriangleByElementWiseAddition
import org.scalatest.flatspec.AnyFlatSpec

class CombinationCountSpec extends AnyFlatSpec {
  private case object _factorial extends Factorial

  private case object _addition extends ElementWiseAddition
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

  "CombinationCountByFactorial behavior" should "evaluate 0C0 as 1" in {
    val n = 0
    val k = 0
    val expected = s"${n}C${k} = 1"
    val actual = combinationByFactorial.combinationCount(n, k).toString()
    assert(actual == expected, s"Expected [$expected], actual [$actual]")
  }

  it should "evaluate 1C1 as 1" in {
    val n = 1
    val k = 1
    val expected = s"${n}C${k} = 1"
    val actual = combinationByFactorial.combinationCount(n, k).toString()
    assert(actual == expected, s"Expected [$expected], actual [$actual]")
  }

  it should "evaluate 2C1 as 2" in {
    val n = 2
    val k = 1
    val expected = s"${n}C${k} = 2"
    val actual = combinationByFactorial.combinationCount(n, k).toString()
    assert(actual == expected, s"Expected [$expected], actual [$actual]")
  }

  it should "evaluate 6C4 as 15" in {
    val n = 6 
    val k = 4
    val expected = s"${n}C${k} = 15"
    val actual = combinationByFactorial.combinationCount(n, k).toString()
    assert(actual == expected, s"Expected [$expected], actual [$actual]")
  }

  it should "evaluate 5C2 as 10" in {
    val n = 5
    val k = 2
    val expected = s"${n}C${k} = 10"
    val actual = combinationByFactorial.combinationCount(n, k).toString()
    assert(actual == expected, s"Expected [$expected], actual [$actual]")
  }

  it should "evaluate 52C5 (aka number of possible poker hands out of a 52 card deck) as 2,598,960" in {
    val n = 52
    val k = 5
    val expected = s"${n}C${k} = 2598960"
    val actual = combinationByFactorial.combinationCount(n, k).toString()
    assert(actual == expected, s"Expected [$expected], actual [$actual]")
  }
  
  it should "evaluate 10,000C7 as 1,979,963,788,224,528,536,430,000" in {
    val n = 10000 
    val k = 7
    val expected = s"${n}C${k} = 1979963788224528536430000"
    val actual = combinationByFactorial.combinationCount(n, k).toString()
    assert(actual == expected, s"Expected [$expected], actual [$actual]")
  }


  "CombinationCountByPascalsTriangle behavior" should "evaluate 0C0 as 1" in {
    val n = 0
    val k = 0
    val expected = "0C0 = 1"
    val actual = combinationByPascalsTriangle.combinationCount(n, k).toString()
    assert(actual == expected, s"Expected [$expected], actual [$actual]")
  }

  it should "evaluate 1C1 as 1" in {
    val n = 1
    val k = 1
    val expected = s"${n}C${k} = 1"
    val actual = combinationByPascalsTriangle.combinationCount(n, k).toString()
    assert(actual == expected, s"Expected [$expected], actual [$actual]")
  }

  it should "evaluate 2C1 as 2" in {
    val n = 2
    val k = 1
    val expected = s"${n}C${k} = 2"
    val actual = combinationByPascalsTriangle.combinationCount(n, k).toString()
    assert(actual == expected, s"Expected [$expected], actual [$actual]")
  }

  it should "evaluate 6C4 as 15" in {
    val n = 6 
    val k = 4
    val expected = s"${n}C${k} = 15"
    val actual = combinationByPascalsTriangle.combinationCount(n, k).toString()
    assert(actual == expected, s"Expected [$expected], actual [$actual]")
  }

  it should "evaluate 5C2 as 10" in {
    val n = 5
    val k = 2
    val expected = s"${n}C${k} = 10"
    val actual = combinationByPascalsTriangle.combinationCount(n, k).toString()
    assert(actual == expected, s"Expected [$expected], actual [$actual]")
  }
  
  it should "evaluate 52C5 (aka number of possible poker hands out of a 52 card deck) as 2,598,960" in {
    val n = 52
    val k = 5
    val expected = s"${n}C${k} = 2598960"
    val actual = combinationByPascalsTriangle.combinationCount(n, k).toString()
    assert(actual == expected, s"Expected [$expected], actual [$actual]")
  }

  //// takes way too long using Pascal's Triangle method  
  // it should "evaluate 10,000C7 as 1,979,963,788,224,528,536,430,000" in {
  //   val n = 10000 
  //   val k = 7
  //   val expected = s"${n}C${k} = 1979963788224528536430000"
  //   val actual = combinationByPascalsTriangle.combinationCount(n, k).toString()
  //   assert(actual == expected, s"Expected [$expected], actual [$actual]")
  // }

}
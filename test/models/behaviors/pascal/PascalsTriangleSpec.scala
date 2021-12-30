package models.behaviors.pascal

import models.behaviors.{ Factorial, ElementWiseAddition, ExponentialApproximation }

import org.scalatest.flatspec.AnyFlatSpec
import models.behaviors.ExponentialApproximation

class PascalsTriangleSpec extends AnyFlatSpec {
  private case object _factorial extends Factorial
  private case object pascalByFactorial extends PascalsTriangleByFactorial {
    override type _Factorial = Factorial
    override val factorial: _Factorial = _factorial 
  }
  private case object _addition extends ElementWiseAddition
  private case object pascalByElementWiseAddition extends PascalsTriangleByElementWiseAddition {
    override type _ElementWiseAddition = ElementWiseAddition
    override val addition = _addition 
  }
  private case object formatter extends ExponentialApproximation

  // "PascalsTriangle behavior" should "create 1 row for 0" in {
  //   val value = 0 
  //   val expected = Seq(Seq(BigInt(1)))
  //   val actual = pascalByFactorial.pascalTriangle(value)
  //   assert(expected == actual, s"Expected [$expected], actual [$actual]")
  // }

  // it should "create 2 rows for 1" in {
  //   val value = 1 
  //   val expected = Seq(Seq(BigInt(1)), Seq(BigInt(1), BigInt(1)))
  //   val actual = pascalByFactorial.pascalTriangle(value)
  //   assert(expected == actual, s"Expected [$expected], actual [$actual]")
  // }
  
  "PascalsTriangleByFactorial behavior" should "create 1 row for 1" in {
    val value = 1 
    val expected = Seq(Seq(BigInt(1)))
    val actual = pascalByFactorial.pascalTriangle(value)
    assert(expected == actual, s"Expected [$expected], actual [$actual]")
  }

  it should "create 2 rows for 2" in {
    val value = 2
    val expected = Seq(Seq(BigInt(1)), Seq(BigInt(1), BigInt(1)))
    val actual = pascalByFactorial.pascalTriangle(value)
    assert(expected == actual, s"Expected [$expected], actual [$actual]")
  }

  it should "create 3 rows for 3" in {
    val value = 3
    val expected = Seq(Seq(BigInt(1)), Seq(BigInt(1), BigInt(1)), Seq(BigInt(1), BigInt(2), BigInt(1)))
    val actual = pascalByFactorial.pascalTriangle(value)
    assert(expected == actual, s"Expected [$expected], actual [$actual]")
  }

  it should "create 100 rows for 100" in {
    val value = 100
    val expectedRowCount = 100
    val result = pascalByFactorial.pascalTriangle(value)
    // println("RESULT: " + result) 
    assert(result.length == expectedRowCount, s"Expected length [$expectedRowCount], actual length [${result.length}]")
  }

  it should "create 250 rows for 250" in {
    val value = 250
    val expectedRowCount = 250
    val result = pascalByFactorial.pascalTriangle(value)
    // println("RESULT: " + result) 
    assert(result.length == expectedRowCount, s"Expected length [$expectedRowCount], actual length [${result.length}]")
  }

  "PascalsTriangleByElementWiseAddition behavior" should "create 1 row for 1" in {
    val value = 1 
    val expected = Seq(Seq(BigInt(1)))
    val actual = pascalByElementWiseAddition.pascalTriangle(value)
    assert(expected == actual, s"Expected [$expected], actual [$actual]")
  }

  it should "create 2 rows for 2" in {
    val value = 2
    val expected = Seq(Seq(BigInt(1)), Seq(BigInt(1), BigInt(1)))
    val actual = pascalByElementWiseAddition.pascalTriangle(value)
    assert(expected == actual, s"Expected [$expected], actual [$actual]")
  }

  it should "create 3 rows for 3" in {
    val value = 3
    val expected = Seq(Seq(BigInt(1)), Seq(BigInt(1), BigInt(1)), Seq(BigInt(1), BigInt(2), BigInt(1)))
    val actual = pascalByElementWiseAddition.pascalTriangle(value)
    assert(expected == actual, s"Expected [$expected], actual [$actual]")
  }

  it should "create 75 rows for 75" in {
    val value =75 
    val expectedRowCount =75 
    val result = pascalByElementWiseAddition.pascalTriangle(value)
    assert(result.length == expectedRowCount, s"Expected length [$expectedRowCount], actual length [${result.length}]")

    // val computed = pascalByElementWiseAddition.pascalTriangle(value).take(75).toList.map(formatter.format(_, 7, 2))
    // val computed = pascalByElementWiseAddition.pascalTriangle(value).take(75).toList.map(formatter.format(_, 7, 2).toString())
    // println("RESULT HEY: " + computed)
  }


  it should "create 250 rows for 250" in {
    val value = 250
    val expectedRowCount = 250
    val result = pascalByElementWiseAddition.pascalTriangle(value)
    assert(result.length == expectedRowCount, s"Expected length [$expectedRowCount], actual length [${result.length}]")
  }

  it should "create 500 rows for 500" in {
    val value = 500
    val expectedRowCount = 500
    val result = pascalByElementWiseAddition.pascalTriangle(value)
    assert(result.length == expectedRowCount, s"Expected length [$expectedRowCount], actual length [${result.length}]")
  }

  it should "create 1000 rows for 1000" in {
    val value = 1000
    val expectedRowCount = 1000
    val result = pascalByElementWiseAddition.pascalTriangle(value)
    assert(result.length == expectedRowCount, s"Expected length [$expectedRowCount], actual length [${result.length}]")
  }

}

package models.behaviors

import org.scalatest.flatspec.AnyFlatSpec

class ElementWiseAdditionSpec extends AnyFlatSpec {
  private case object arith extends ElementWiseAddition

  "Arithmetic behavior" should "add [0, 1, 2] and [3, 4, 5]" in {
    val value1 = List(0, 1, 2)
    val value2 = List(3, 4, 5)
    val expected = List(3, 5, 7)
    val actual = arith.add(value1, value2)
    assert(actual == expected, s"Expected [$expected], actual [$actual]")
  }

  it should "add [0] and [0]" in {
    val value1 = List(0)
    val value2 = List(0)
    val expected = List(0)
    val actual = arith.add(value1, value2)
    assert(actual == expected, s"Expected [$expected], actual [$actual]")
  }

  it should "add [0, 0] and [0, 0]" in {
    val value1 = List(0, 0)
    val value2 = List(0, 0)
    val expected = List(0, 0)
    val actual = arith.add(value1, value2)
    assert(actual == expected, s"Expected [$expected], actual [$actual]")
  }

  it should "add [] and []" in {
    val value1 = Nil: List[Int] 
    val value2 = Nil: List[Int] 
    val expected = Nil 
    val actual = arith.add(value1, value2)
    assert(actual == expected, s"Expected [$expected], actual [$actual]")
  }

  it should "add [1, -5] and [1, -5]" in {
    val value1 = List(1, -5)
    val value2 = List(1, -5)
    val expected = List(2, -10)
    val actual = arith.add(value1, value2)
    assert(actual == expected, s"Expected [$expected], actual [$actual]")
  }

  it should "add [1] and [] as [1]" in {
    val value1 = List(1)
    val value2 = Nil: List[Int]
    val expected = List(1)
    val actual = arith.add(value1, value2)
    assert(actual == expected, s"Expected [$expected], actual [$actual]")
  }
  
  it should "add [] and [1] as [1]" in {
    val value1 = Nil: List[Int]
    val value2 = List(1)
    val expected = List(1)
    val actual = arith.add(value1, value2)
    assert(actual == expected, s"Expected [$expected], actual [$actual]")
  }

  it should "add [1, 2] and [1] as [2, 2] " in {
    val value1 = List(1, 2) 
    val value2 = List(1)
    val expected = List(2, 2)
    val actual = arith.add(value1, value2)
    assert(actual == expected, s"Expected [$expected], actual [$actual]")
  }

  it should "add [1, 2, 3, 4] and [1, 2] as [2, 4, 3, 4] " in {
    val value1 = List(1, 2, 3, 4)  
    val value2 = List(1, 2)  
    val expected = List(2, 4, 3, 4)
    val actual = arith.add(value1, value2)
    assert(actual == expected, s"Expected [$expected], actual [$actual]")
  }

  it should "add [1, 2, 3, 4] and [] as [1, 2, 3, 4]" in {
    val value1 = List(1, 2, 3, 4)
    val value2 = Nil: List[Int]
    val expected = List(1, 2, 3, 4)
    val actual = arith.add(value1, value2)
    assert(actual == expected, s"Expected [$expected], actual [$actual]")
  }

  it should "add [] and [1, 2, 3, 4] as [1, 2, 3, 4]" in {
    val value1 = Nil: List[Int]
    val value2 = List(1, 2, 3, 4)
    val expected = List(1, 2, 3, 4)
    val actual = arith.add(value1, value2)
    assert(actual == expected, s"Expected [$expected], actual [$actual]")
  }

}
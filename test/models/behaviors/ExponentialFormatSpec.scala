package models.behaviors

import org.scalatest.flatspec.AnyFlatSpec

class ExponentialFormatSpec extends AnyFlatSpec {
  private case object formatter extends ExponentialFormat

  "ExponentialFormat behavior" should "not format 0 with max digits 4 and scale 2" in {
    val value = BigInt(0)
    val maxLength = 4
    val scale = 2
    val expected = "0"
    val actual = formatter.format(value, maxLength, scale)
    assert(actual == expected, s"Expected[$expected], actual [$actual]")
  }

  it should "not format 300 with max digits 4 and scale 2" in {
    val value = BigInt(300)
    val maxLength = 4
    val scale = 2
    val expected = "300"
    val actual = formatter.format(value, maxLength, scale)
    assert(actual == expected, s"Expected[$expected], actual [$actual]")
  }

  it should "not format 300 with max digits 3 and scale 2" in {
    val value = BigInt(300)
    val maxLength = 3
    val scale = 2
    val expected = "300"
    val actual = formatter.format(value, maxLength, scale)
    assert(actual == expected, s"Expected[$expected], actual [$actual]")
  }

  it should "format 300 with max digits 2 and scale 2 as [3.00E2]" in {
    val value = BigInt(300)
    val maxLength = 2
    val scale = 2
    val expected = "3.00E2"
    val actual = formatter.format(value, maxLength, scale)
    assert(actual == expected, s"Expected[$expected], actual [$actual]")
  }

  it should "format 300 with max digits 1 and scale 2 as [3.00E2]" in {
    val value = BigInt(300)
    val maxLength = 1
    val scale = 2
    val expected = "3.00E2"
    val actual = formatter.format(value, maxLength, scale)
    assert(actual == expected, s"Expected[$expected], actual [$actual]")
  }

  it should "not format 9,999,999 with max digits 7 and scale 2" in {
    val value = BigInt("9,999,999".replace(",", ""))
    val maxLength = 7
    val scale = 2
    val expected = "9999999"
    val actual = formatter.format(value, maxLength, scale)
    assert(actual == expected, s"Expected[$expected], actual [$actual]")
  }

  it should "format 99,999,999 with max digits 7 and scale 2 as [1.00E8]" in {
    val value = BigInt("99,999,999".replace(",", ""))
    val maxLength = 7
    val scale = 2
    val expected = "1.00E8"
    val actual = formatter.format(value, maxLength, scale)
    assert(actual == expected, s"Expected[$expected], actual [$actual]")
  }

  it should "not format 8,999,999 with max digits 7 and scale 2" in {
    val value = BigInt("8,999,999".replace(",", ""))
    val maxLength = 7
    val scale = 2
    val expected = "8999999"
    val actual = formatter.format(value, maxLength, scale)
    assert(actual == expected, s"Expected[$expected], actual [$actual]")
  }

  it should "format 8,999,999 with max digits 6 and scale 2 as [9.00E6]" in {
    val value = BigInt("8,999,999".replace(",", ""))
    val maxLength = 6
    val scale = 2
    val expected = "9.00E6"
    val actual = formatter.format(value, maxLength, scale)
    assert(actual == expected, s"Expected[$expected], actual [$actual]")
  }

  it should "format 8,555,554 with max digits 6 and scale 2 as [8.56E6]" in {
    val value = BigInt("8,555,554".replace(",", ""))
    val maxLength = 6
    val scale = 2
    val expected = "8.56E6"
    val actual = formatter.format(value, maxLength, scale)
    assert(actual == expected, s"Expected[$expected], actual [$actual]")
  }

  it should "format 8,545,554 with max digits 6 and scale 2 as [8.55E6]" in {
    val value = BigInt("8,545,554".replace(",", ""))
    val maxLength = 6
    val scale = 2
    val expected = "8.55E6"
    val actual = formatter.format(value, maxLength, scale)
    assert(actual == expected, s"Expected[$expected], actual [$actual]")
  }

  it should "format [777, 1,839,462, 21,340,100, 430,400,607, 2, 0] with max digits 7 and scale 2 as [777, 1839462, 2.13E7, 4.30E8, 2, 0]" in {
    val values = 
      Seq(
        BigInt(777),
        BigInt("1,839,462".replace(",", "")),
        BigInt("21,340,100".replace(",", "")),
        BigInt("430,400,607".replace(",", "")),
        BigInt(2),
        BigInt(0) 
      )
    val maxLength = 7
    val scale = 2
    val expected: Seq[String] = Seq("777", "1839462", "2.13E7", "4.30E8", "2", "0")
    val actual = formatter.format(values, maxLength, scale)
    assert(actual == expected, s"Expected[$expected], actual [$actual]")
  }

  it should "not format an empty list" in {
    val values: Seq[BigInt] = Nil
    val maxLength = 7
    val scale = 2
    val expected: Seq[String] = Nil
    val actual = formatter.format(values, maxLength, scale)
    assert(actual == expected, s"Expected[$expected], actual [$actual]")
  }

  it should "not format 300 with max digits 3 and scale 4" in {
    val value = BigInt(300)
    val maxLength = 3
    val scale = 4
    val expected = "300"
    val actual = formatter.format(value, maxLength, scale)
    assert(actual == expected, s"Expected[$expected], actual [$actual]")
  }

  it should "format 300 with max digits 2 and scale 4 as [3.0000E2]" in {
    val value = BigInt(300)
    val maxLength = 2
    val scale = 4
    val expected = "3.0000E2"
    val actual = formatter.format(value, maxLength, scale)
    assert(actual == expected, s"Expected[$expected], actual [$actual]")
  }

  it should "format 300 with max digits 1 and scale 4 as [3.0000E2]" in {
    val value = BigInt(300)
    val maxLength = 1
    val scale = 4
    val expected = "3.0000E2"
    val actual = formatter.format(value, maxLength, scale)
    assert(actual == expected, s"Expected[$expected], actual [$actual]")
  }

  it should "not format 9,999,999 with max digits 7 and scale 4" in {
    val value = BigInt("9,999,999".replace(",", ""))
    val maxLength = 7
    val scale = 4
    val expected = "9999999"
    val actual = formatter.format(value, maxLength, scale)
    assert(actual == expected, s"Expected[$expected], actual [$actual]")
  }

  it should "format 99,999,999 with max digits 7 and scale 4 as [1.0000E8]" in {
    val value = BigInt("99,999,999".replace(",", ""))
    val maxLength = 7
    val scale = 4
    val expected = "1.0000E8"
    val actual = formatter.format(value, maxLength, scale)
    assert(actual == expected, s"Expected[$expected], actual [$actual]")
  }

  it should "not format 8,999,999 with max digits 7 and scale 4" in {
    val value = BigInt("8,999,999".replace(",", ""))
    val maxLength = 7
    val scale = 4
    val expected = "8999999"
    val actual = formatter.format(value, maxLength, scale)
    assert(actual == expected, s"Expected[$expected], actual [$actual]")
  }

  it should "format 8,999,999 with max digits 6 and scale 4 as [9.0000E6]" in {
    val value = BigInt("8,999,999".replace(",", ""))
    val maxLength = 6
    val scale = 4
    val expected = "9.0000E6"
    val actual = formatter.format(value, maxLength, scale)
    assert(actual == expected, s"Expected[$expected], actual [$actual]")
  }

  it should "format 8,555,554 with max digits 6 and scale 4 as [8.5556E6]" in {
    val value = BigInt("8,555,554".replace(",", ""))
    val maxLength = 6
    val scale = 4
    val expected = "8.5556E6"
    val actual = formatter.format(value, maxLength, scale)
    assert(actual == expected, s"Expected[$expected], actual [$actual]")
  }

  it should "format 8,545,554 with max digits 6 and scale 4 as [8.5456E6]" in {
    val value = BigInt("8,545,554".replace(",", ""))
    val maxLength = 6
    val scale = 4
    val expected = "8.5456E6"
    val actual = formatter.format(value, maxLength, scale)
    assert(actual == expected, s"Expected[$expected], actual [$actual]")
  }

  it should "format [777, 1,839,462, 21,340,100, 430,400,607, 2, 0] with max digits 7 and scale 4 as [777, 1839462, 2.1340E7, 4.3040E8, 2, 0]" in {
    val values = 
      Seq(
        BigInt(777),
        BigInt("1,839,462".replace(",", "")),
        BigInt("21,340,100".replace(",", "")),
        BigInt("430,400,607".replace(",", "")),
        BigInt(2),
        BigInt(0) 
      )
    val maxLength = 7
    val scale = 4
    val expected: Seq[String] = Seq("777", "1839462", "2.1340E7", "4.3040E8", "2", "0")
    val actual = formatter.format(values, maxLength, scale)
    assert(actual == expected, s"Expected[$expected], actual [$actual]")
  }

}
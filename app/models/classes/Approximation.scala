package models.classes

import play.api.libs.json.{ Json, Format, JsValue }
import java.text.NumberFormat

case class Approximation(actual: BigInt, approximation: String) {
  override def toString(): String = {
    (Json.obj(
      "actual" -> NumberFormat.getIntegerInstance().format(actual), // add commas
      "approximation" -> approximation
    )).toString()
  }
}
object Approximation {
  implicit val jsonFormat: Format[Approximation] = Json.format[Approximation]
}

case class Approximations(approximations: Seq[Approximation]) {
  override def toString(): String = {
    (approximations.map { a => 
      Json.obj(
        "actual" -> NumberFormat.getIntegerInstance().format(a.actual), // add commas
        "approximation" -> a.approximation
      ).toString()}).mkString("""{"row":[""", ",", "]}")
    }
  
  def map[A](f: (Approximation) => A): Seq[A] = approximations.map(f(_))
}
object Approximations {
  implicit val jsonFormat: Format[Approximations] = Json.format[Approximations]
}
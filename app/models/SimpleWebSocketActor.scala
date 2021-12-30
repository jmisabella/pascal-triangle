package models

import models.modules.Triangle

import akka.actor._
import play.api.libs.json._
import play.api.libs.json.Json
import models.classes.{ Approximation, Approximations }

object SimpleWebSocketActor {
  // DOCS: Props is a ActorRef configuration object, that is immutable, so it is 
  // thread-safe and fully sharable. Used when creating new actors through 
  // ActorSystem.actorOf and ActorContext.actorOf.
  def props(clientActorRef: ActorRef) = Props(new SimpleWebSocketActor(clientActorRef))
}

class SimpleWebSocketActor(clientActorRef: ActorRef) extends Actor {
  val logger = play.api.Logger(getClass)

  logger.info(s"SimpleWebSocketActor class started")

  // this is where we receive json messages sent by the client
  // and send them a json reply
  def receive = {
    case jsValue: JsValue =>
      logger.info(s"JS-VALUE: $jsValue")
      val clientMessage = getMessage(jsValue)

      val rows = clientMessage.toInt

      println("beginning processing...") 

      // val result0: Seq[Seq[BigInt]] = Triangle.pascalTriangle(rows)
      // val result1: Seq[Approximations] = result0.map(x => Triangle.format(x, 6, 2)).toList
      // val result = result1.mkString("""{ "rows": [""", ", ", """]}""")

      val result: String = Triangle.formattedTriangleJson(rows, 6, 2)
      // println("HEYYY: " + result)

      val json: JsValue = Json.parse(s"""{ "body": $result }""")
      // val json: JsValue = Json.parse(s"""{"body": "You said, ‘$clientMessage’"}""")
      clientActorRef ! (json)
  }

  // parse the "message" field from the json the client sends us
  def getMessage(json: JsValue): String = (json \ "message").as[String]

}

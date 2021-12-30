package models

import models.modules.Triangle

import akka.actor._
import play.api.libs.json._
import play.api.libs.json.Json
import models.classes.Approximation

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
      var triangle = Triangle.pascalTriangle(rows).map(Triangle.format(_, 6, 2))
      var result: Seq[Seq[Approximation]] = Nil
      for (i <- 0 until rows) {
        result = result ++ Seq(triangle.head)
        triangle = triangle.tail
      }
      println("HEYYY: " + result)

      val json: JsValue = Json.parse(s"""{"body": "You said, ‘$clientMessage’"}""")
      clientActorRef ! (json)
  }

  // parse the "message" field from the json the client sends us
  def getMessage(json: JsValue): String = (json \ "message").as[String]

}

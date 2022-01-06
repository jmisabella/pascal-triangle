package models

import models.modules.{ Triangle, Combo, Prob }

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

  private def parseArgs(rawMsg: String): Map[String, String] = rawMsg.split(";").map(kv => kv.split("=").head -> kv.split("=").tail.head).toMap

  // this is where we receive json messages sent by the client
  // and send them a json reply
  def receive = {
    case jsValue: JsValue =>
      logger.info(s"JS-VALUE: $jsValue")
      val clientMessage = getMessage(jsValue)

      // TODO: parse args
      val args: Map[String, String] = parseArgs(clientMessage)
      val result: String = 
      args.keySet match {
        case s if (s == Set("rows")) => Triangle.formattedResultJson(args("rows").toInt, 6, 2)
        case s if (s == Set("combination-n", "combination-k")) => Combo.formattedResultJson(args("combination-n").toInt, args("combination-k").toInt, 6, 2)
        case s if (s == Set("probability-n", "probability-k")) => Prob.formattedResultJson(args("probability-n").toInt, args("probability-k").toInt, 6, 2)
        case u => throw new IllegalArgumentException(s"From args [$clientMessage], unexpected arg key combination [$u]")
      } 

      println("beginning processing...") 

      val json: JsValue = Json.parse(s"""{ "body": $result }""")
      // val json: JsValue = Json.parse(s"""{"body": "You said, ‘$clientMessage’"}""")
      clientActorRef ! (json)
  }

  // parse the "message" field from the json the client sends us
  def getMessage(json: JsValue): String = (json \ "message").as[String]

}

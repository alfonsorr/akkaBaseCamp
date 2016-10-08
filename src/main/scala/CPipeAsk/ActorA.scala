package CPipeAsk

import akka.actor.{Actor, ActorRef, Props}
import akka.util.Timeout

object ActorAppendString{
  def props(textToAppend:String, nextActor: Option[ActorRef] = None) = Props(new ActorAppendString(nextActor, textToAppend ))
}

class ActorAppendString(next:Option[ActorRef], textToAppend:String) extends Actor{
  override def receive: Receive = {
    case text:String =>
      val fullText = text + textToAppend
      next match {
        case Some(actor) => actor forward fullText
        case None => sender() ! fullText
      }
  }
}

object ActorStart{
  object Start
  def props(firstAppenderActor:ActorRef) = Props(new ActorStart(firstAppenderActor))
}

class ActorStart(next:ActorRef) extends Actor {
  import akka.pattern.ask
  import scala.concurrent.duration._
  import akka.util.Timeout._

  implicit val executionContext = context.dispatcher


  implicit val duration:Timeout = 20 seconds

  override def receive: Receive = {
    case ActorStart.Start => (next ? "").mapTo[String].map(println)
  }
}


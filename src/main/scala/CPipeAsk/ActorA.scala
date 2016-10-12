package CPipeAsk

import CPipeAsk.ActorCoordinator.{CleanList, SetWords, Start}
import akka.actor.{Actor, ActorLogging, ActorRef, PoisonPill, Props}
import akka.util.Timeout

import scala.util.Random

object ActorAppendString{
  def props(textToAppend:String, nextActor: Option[ActorRef] = None) = Props(new ActorAppendString(nextActor, textToAppend ))
}

class ActorAppendString(next:Option[ActorRef], textToAppend:String) extends Actor with ActorLogging{
  override def receive: Receive = {
    case text:String =>
      val fullText = text + textToAppend
      next match {
        case Some(actor) =>
          log.info(s"appending $textToAppend")
          actor forward fullText //FORWARD
        case None =>
          log.info(s"appending $textToAppend")
          sender() ! fullText
      }
  }
}

object ActorCoordinator {
  def props = Props[ActorCoordinator]
  case class SetWords(list:List[String])
  case object CleanList
  case object Start
}

class ActorCoordinator extends Actor with ActorLogging{

  import akka.pattern.ask
  import scala.concurrent.duration._
  import akka.util.Timeout._

  implicit val executionContext = context.dispatcher
  implicit val duration:Timeout = 2 seconds

  var actorsList = List.empty[ActorRef]

  def createChilds(listOfWords:List[String]):List[ActorRef] = {
    listOfWords.reverse.foldLeft(List.empty[ActorRef])((nextActor, word) =>
      context.actorOf(ActorAppendString.props(word, nextActor.headOption), s"$word$$${Random.nextInt()}") :: nextActor)
  }


  override def receive: Receive = {
    case SetWords(wordList) => actorsList = createChilds(wordList)
    case Start => actorsList.headOption.foreach(actor => {
      val future = (actor ? "").mapTo[String]  // ASK
      future.map(log.info)
      future.onFailure{case t => log.error(t.getMessage)}
    })
    case CleanList =>
      actorsList.foreach(_ ! PoisonPill)
      actorsList = List.empty[ActorRef]
  }
}

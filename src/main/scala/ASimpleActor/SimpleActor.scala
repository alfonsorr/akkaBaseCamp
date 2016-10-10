package ASimpleActor

import ASimpleActor.SimpleActor.{PrintAll, Put, PutTwice}
import akka.actor.{Actor, ActorLogging, ActorSystem, Props}

object SimpleActor {
  def props = Props[SimpleActor]
  case class PutTwice(s:String)
  case class Put(s:String)
  case object PrintAll
}

class SimpleActor extends Actor with ActorLogging{

  var list:List[String] = List.empty[String]

  override def receive: Receive = {
    case PutTwice(s) => list = s :: s :: list
    case Put(s) => list = s :: list
    case PrintAll => println(list.mkString(", "))
    case _ => log.error("not expected")
  }
}

object SimpleActorMain extends App {
  val system = ActorSystem("simpleActor")

  val simpleActor = system.actorOf(SimpleActor.props)

  simpleActor ! Put("hola")
  simpleActor ! 42
  simpleActor ! PutTwice("a message")

  println("enter to continue")
  scala.io.StdIn.readLine()
  simpleActor ! PrintAll

  println("enter to continue")
  scala.io.StdIn.readLine()

  system.terminate()

}
package ASimpleActor

import ASimpleActor.SimpleActor.{PrintAll, Put, PutTwice}
import akka.actor.{Actor, ActorLogging, Props}

object SimpleActor {
  def props = Props[SimpleActor] // GOOD MANNERS
  case class PutTwice(s:String)
  case class Put(s:String)
  case object PrintAll
}

class SimpleActor extends Actor with ActorLogging{

  var list:List[String] = List.empty[String]

  override def receive: Receive = { // RECEIVE
    case PutTwice(s) => list = s :: s :: list
    case Put(s) => list = s :: list
    case PrintAll => println(list.mkString(", "))
    case _ => log.error("not expected")
  }
}


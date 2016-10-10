package DBecome

import ASimpleActor.SimpleActor.{PrintAll, Put, PutTwice}
import akka.actor.{Actor, ActorSystem, Props}

object SimpleInmutableActor {
  def props = Props[SimpleInmutableActor]
  case class PutTwice(s:String)
  case class Put(s:String)
  case object PrintAll
}

class SimpleInmutableActor extends Actor{

  override def receive: Receive = receive(List.empty[String])

  def receive(list:List[String]):Receive = {
    case PutTwice(s) =>
      val listActual =  s :: s :: list
      context.become(receive(listActual), discardOld = true)
    case Put(s) =>
      val listActual = s :: list
      context.become(receive(listActual), discardOld = true)
    case PrintAll => println(list.mkString(", "))
    case _ => println("not a string")
  }
}

object SimpleInmutableActorMain extends App {
  val system = ActorSystem("simpleActor")

  val simpleActor = system.actorOf(SimpleInmutableActor.props)


  simpleActor ! "hola"
  simpleActor ! 42
  simpleActor ! PutTwice("a message")


  println("enter to continue")
  scala.io.StdIn.readLine()

  system.terminate()
}
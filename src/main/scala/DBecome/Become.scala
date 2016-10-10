package DBecome

import ASimpleActor.SimpleActor.PutTwice
import DBecome.OrderActor.{A, B, C}
import akka.actor.{Actor, ActorLogging, ActorSystem, Props}

object OrderActor {
  def props = Props[OrderActor]
  case object A
  case object B
  case object C
}

class OrderActor extends Actor with ActorLogging{
  override def receive: Receive = receiveA

  def receiveA:Receive = {
    case A => context.become(receiveB)
    case _ => log.error("that was unexpected (and not an A)")
  }

  def receiveB:Receive = {
    case B => context.become(receiveC)
    case _ => log.error("that was unexpected (and not an B)")
  }

  def receiveC:Receive = {
    case C => context.become(receiveA)
    case _ => log.error("that was unexpected (and not an C)")
  }
}

object BecomeMain extends App {
  val system = ActorSystem("becomeActor")

  val simpleActor = system.actorOf(OrderActor.props)

  for {
    _ <- 1 to 3
  } yield {
    simpleActor ! A
    simpleActor ! B
    simpleActor ! C
  }

  println("enter to continue")
  scala.io.StdIn.readLine()

  simpleActor ! A
  simpleActor ! A
  simpleActor ! B
  simpleActor ! B
  simpleActor ! C
  simpleActor ! C

  println("enter to continue")
  scala.io.StdIn.readLine()

  system.terminate()
}
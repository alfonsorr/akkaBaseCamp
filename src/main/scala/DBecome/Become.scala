package DBecome

import ASimpleActor.SimpleActor.PutTwice
import DBecome.OrderActor.{A, B, C}
import akka.actor.{Actor, ActorSystem, Props}

object OrderActor {
  def props = Props[OrderActor]
  case object A
  case object B
  case object C
}

class OrderActor extends Actor{
  override def receive: Receive = receiveA

  def receiveA:Receive = {
    case A => context.become(receiveB)
    case _ => println("that was unexpected")
  }

  def receiveB:Receive = {
    case B => context.become(receiveC)
    case _ => println("that was unexpected")
  }

  def receiveC:Receive = {
    case C => context.become(receiveA)
    case _ => println("that was unexpected")
  }
}

object SimpleActorMain extends App {
  val system = ActorSystem("simpleActor")

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
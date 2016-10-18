package DBecome

import DBecome.OrderActor.{A, B, C, Mess}
import akka.actor.{Actor, ActorLogging, ActorSystem, Props}

object OrderActor {
  def props = Props[OrderActor]
  trait Mess
  case object A extends Mess
  case object B extends Mess
  case object C extends Mess
}

class OrderActor extends Actor with ActorLogging{
  override def receive: Receive = receiveA
  //override def receive: Receive = receiveGen(A)

  def receiveA:Receive = {
    case A => context.become(receiveB, discardOld = true) // Become
    case _ => log.error("that was unexpected (and not an A)")
  }

  def receiveB:Receive = {
    case B => context.become(receiveC, discardOld = true)
    case _ => log.error("that was unexpected (and not an B)")
  }

  def receiveC:Receive = {
    case C => context.become(receiveA, discardOld = true)
    case _ => log.error("that was unexpected (and not an C)")
  }

  //Alternative and more generic way
  /*private def nextExpectedMessage:Mess => Mess = {
    case A => B
    case B => C
    case C => A
  }

  def receiveGen(expected:Mess):Receive = {
    case a:Mess if a==expected => context.become(receiveGen(nextExpectedMessage(a)))
    case _ => log.error(s"that was unexpected (and not an $expected)")
  }*/
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
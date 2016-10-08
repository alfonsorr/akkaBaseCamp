package BPingPong

import akka.actor.{Actor, ActorRef, Props}

object ActorPing {
  def props(actorPong:ActorRef) = Props(new ActorPing(actorPong))
}

class ActorPing(actorPong:ActorRef) extends Actor{
  override def receive: Receive = {
    case "ping" =>
      println("ping")
      actorPong ! "pong"
  }
}

object ActorPong {
  def props = Props[ActorPong]
}

class ActorPong extends Actor{
  override def receive: Receive = {
    case "pong" =>
      println("pong")
      sender() ! "ping"
  }
}

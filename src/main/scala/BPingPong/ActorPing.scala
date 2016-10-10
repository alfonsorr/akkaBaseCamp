package BPingPong

import BPingPong.ActorPing.PingTo
import akka.actor.{Actor, ActorRef, Props}

object ActorPing {
  def props = Props[ActorPing]
  case class PingTo(actor:ActorRef)
}

class ActorPing extends Actor{
  override def receive: Receive = {
    case PingTo(actor) => actor ! "ping"
    case "pong" =>
      println("ping")
      sender() ! "ping"
  }
}

object ActorPong {
  def props = Props[ActorPong]
}

class ActorPong extends Actor{
  override def receive: Receive = {
    case "ping" =>
      println("pong")
      sender() ! "pong"
  }
}

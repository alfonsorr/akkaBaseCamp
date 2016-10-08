package BPingPong

import akka.actor.ActorSystem

object PingPongMain extends App{

  val system = ActorSystem("pingpong")

  val apong = system.actorOf(ActorPong.props)
  val actorPing = system.actorOf(ActorPing.props(apong))

  actorPing ! "ping"

}

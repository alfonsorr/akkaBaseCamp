package BPingPong

import BPingPong.ActorPing.PingTo
import akka.actor.ActorSystem

object
PingPongMain extends App {

  val system = ActorSystem("pingpong")

  val actorPing = system.actorOf(ActorPing.props)
  val actorPong = system.actorOf(ActorPong.props)

  actorPing ! PingTo(actorPong)

  println("enter to continue")
  scala.io.StdIn.readLine()

  system.terminate()
}

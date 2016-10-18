package ASimpleActor

import ASimpleActor.SimpleActor.{PrintAll, Put, PutTwice}
import akka.actor.{ActorRef, ActorSystem}

object SimpleActorMain extends App {
  val system = ActorSystem("simpleActor") // ACTOR SYSTEM CREATION

  val simpleActor:ActorRef = system.actorOf(SimpleActor.props) //ACTOR REF

  simpleActor ! Put("hola") // TELL
  simpleActor ! 42
  simpleActor ! PutTwice("a message")

  println("enter to continue")
  scala.io.StdIn.readLine()
  simpleActor ! PrintAll

  println("enter to continue")
  scala.io.StdIn.readLine()

  system.terminate()

}

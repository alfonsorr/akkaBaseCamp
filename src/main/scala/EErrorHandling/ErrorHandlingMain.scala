package EErrorHandling

import akka.actor.ActorSystem

/**
  * Created by alf on 10/10/16.
  */
object ErrorHandlingMain extends App{
  val system = ActorSystem("errorActor")
  system.actorOf(Father.props, "myFather")

  println("enter to continue")
  scala.io.StdIn.readLine()
}

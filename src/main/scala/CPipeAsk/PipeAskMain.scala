package CPipeAsk

import CPipeAsk.ActorCoordinator.{CleanList, SetWords, Start}
import akka.actor.ActorSystem

object PipeAskMain extends App {
  val system = ActorSystem("ask")

  var wordList1 = List("Let", "It", "Crash")

  val actorCoord = system.actorOf(ActorCoordinator.props)

  actorCoord ! Start
  println("enter to continue")
  scala.io.StdIn.readLine()

  actorCoord ! SetWords(wordList1)
  actorCoord ! Start
  actorCoord ! CleanList

  println("enter to continue")
  scala.io.StdIn.readLine()

  system.terminate()

}


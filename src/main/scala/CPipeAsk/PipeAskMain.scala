package CPipeAsk

import CPipeAsk.ActorCoordinator.{CleanList, SetWords, Start}
import akka.actor.ActorSystem

object PipeAskMain extends App {
  val system = ActorSystem("ask")

  var wordList1 = List("Let", "It", "Crash")
  var wordList2 = (for {
    l1 <- 'a' to 'z'
    l2 <- 'a' to 'z'
    l3 <- 'a' to 'z'
    l4 <- 'a' to 'z'
    l5 <- 'a' to 'z'
  } yield s"$l1$l2$l3$l4$l5"
    ).toList

  val actorCoord = system.actorOf(ActorCoordinator.props)

  actorCoord ! Start
  println("enter to continue")
  scala.io.StdIn.readLine()

  actorCoord ! SetWords(wordList1)
  actorCoord ! Start
  actorCoord ! CleanList

  println("enter to continue")
  scala.io.StdIn.readLine()

  actorCoord ! SetWords(wordList2)
  actorCoord ! Start
  actorCoord ! CleanList

  println("enter to continue")
  scala.io.StdIn.readLine()

  system.terminate()

}


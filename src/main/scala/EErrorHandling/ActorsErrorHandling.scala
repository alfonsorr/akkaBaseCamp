package EErrorHandling

import EErrorHandling.Child.Next
import EErrorHandling.Father.Counter
import akka.actor.SupervisorStrategy.{Escalate, Restart, Resume, Stop}
import akka.actor.{Actor, ActorLogging, OneForOneStrategy, Props}



object Father {
  def props = Props[Father]
  case class Counter(n:Int)
}

class Father extends Actor with ActorLogging{

  import scala.concurrent.duration._

  val childActor = context.actorOf(Child.props, "myChild")

  implicit val exectionContext = context.dispatcher

  context.system.scheduler.schedule(1 second, 500 milliseconds, childActor, Next)

  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 4, withinTimeRange = 1 minute) {
      case _: ArithmeticException      => Resume
      case _: NumberFormatException    => Restart
      case _: IllegalArgumentException => Stop
      case _: Exception                => Escalate
    }

  override def receive: Receive = {
    case Counter(n) => log.info(s"received: $n")
    case _ => log.info("something")
  }
}

object Child {
  def props = Props[Child]
  case object Next
}

class Child extends Actor {

  var counter:Int = 0

  def doSomething(): Unit = {
    if (counter == 5) {
      throw new ArithmeticException("5 is too ugly")
    } else if (counter == 9) {
      throw new NumberFormatException("9 is too mainstream")
    }
  }



  override def receive: Receive = {
    case Next =>
      counter = counter +1
      doSomething()
      sender() ! Counter(counter)
  }
}
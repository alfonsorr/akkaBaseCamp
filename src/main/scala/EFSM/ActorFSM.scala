package EFSM

import akka.actor.FSM

trait State
case object Sleeping extends State
case object Working extends State

trait Data
case object Pillow extends Data
case class BigDataWork()

class ActorFSM extends FSM[State,Data] {


}

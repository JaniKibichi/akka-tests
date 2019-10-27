package com.janikibichi.models

import akka.actor.{ActorRef, ActorSystem, LoggingFSM, Props}

object DonutBakingActor{

  val system = ActorSystem("DonutActorFSM")

  sealed trait BakingEvents
  final case object BakeDonut extends BakingEvents
  final case object AddTopping extends BakingEvents
  final case object StopBaking extends BakingEvents

  sealed trait BakingStates
  case object Start extends BakingStates
  case object Stop extends BakingStates

  case class BakingData(qty:Int){
    def addOneDonut:DonutBakingActor.BakingData = copy(qty + 1)
  }

  object BakingData{
    def initialQuantity = BakingData(0)
  }

  class DonutBakingActor extends LoggingFSM[BakingStates,BakingData]{
    startWith(Stop, BakingData.initialQuantity)

    when(Stop){
      case Event(BakeDonut,_) =>
        println("Current state is [Stop],switching to [Start]")
        goto(Start).using(stateData.addOneDonut)

      case Event(AddTopping,_) =>
        println("Current state is [Stop], you first need to move to [BakeDonut]")
        stay()
    }

    import scala.concurrent.duration._
    when(Start,stateTimeout = 5.second){
      case Event(StopBaking,_) =>
        println(s"Event StopBaking, current donut quantity = ${stateData.qty}")
        goto(Stop)

      case Event(StateTimeout,_) =>
        println("Timing out state = [Start], switching to state = [Stop]")
        goto(Stop)
    }

    whenUnhandled{
      case Event(event,stateData) =>
        println(s"We received an unhandled event = [$event] for the state data = [$stateData]")
        goto(Stop)
    }

    onTransition{
      case Stop -> Start =>
        println("Switching state from [Stop -> Start]")
      case Start -> Stop =>
        println("Switching state from [Start -> Stop]")
    }

    import akka.actor.FSM._
    onTermination{
      case StopEvent(Normal,state,data) =>
        log.info(s"Actor onTermination, event = Normal, state =$state, data = $data")

      case StopEvent(Shutdown,state,data) =>
        log.info(s"Actor onTermination, event = Shutdown, state =$state, data =$data")

      case StopEvent(Failure(cause),state,data) =>
        log.error(s"Actor onTermination, event=Failure, cause = $cause, state=$state, data=$data")
    }

    initialize()
  }

  val bakingActor:ActorRef = system.actorOf(Props[DonutBakingActor],"donut-baking-actor")

  bakingActor ! AddTopping
  Thread.sleep(2000)

  bakingActor ! BakeDonut
  Thread.sleep(10000)

  bakingActor ! StopBaking
  Thread.sleep(2000)

  bakingActor ! AddTopping
  Thread.sleep(2000)

  system.terminate()
}

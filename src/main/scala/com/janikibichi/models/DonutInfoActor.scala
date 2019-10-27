package com.janikibichi.models

import akka.actor.{Actor, ActorLogging}
import com.janikibichi.models.DonutStoreProtocol.Info

object DonutStoreProtocol{
  case class Info(name:String)
}

class DonutInfoActor extends Actor with ActorLogging{
  def receive: Receive = {
    case Info(name) if name == "vanilla" =>
      log.info(s"Found valid $name donut")
      sender() ! true

    case Info(name) =>
      log.info(s"$name donut is not supported")
      sender() ! false

    case event @ _ =>
      log.info(s"Event $event is not allowed")
      throw new IllegalStateException(s"Event $event is not allowed")

  }
}
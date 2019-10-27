package com.janikibichi.models

import akka.actor.Actor
import com.janikibichi.models.CounterProtocol.{Decrement, GetCount, Increment}

object CounterProtocol{
  case object Increment
  case object Decrement
  case object GetCount
}

class CounterActor extends Actor{
  var count: Int = 0

  def receive: Receive = {
    case Increment =>
      count += 1

    case Decrement =>
      count -= 1

    case GetCount =>
      sender() ! count
  }
}
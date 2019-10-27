package com.janikibichi.util

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.ExecutionContextExecutor

object AppUtil{

  implicit val requestTimeout:Timeout = 200.seconds
  implicit val actorSystem:ActorSystem = ActorSystem("donut-akka-http-server")
  implicit val mat: ActorMaterializer = ActorMaterializer()
  implicit val executionContextExecutor: ExecutionContextExecutor = actorSystem.dispatcher

}
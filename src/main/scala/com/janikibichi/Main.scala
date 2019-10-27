package com.janikibichi

import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import com.janikibichi.routes.Routes
import com.janikibichi.util.DonutConfig.http
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Future
import scala.io.StdIn
import scala.util.{Failure, Success}

object Main extends App with LazyLogging with Routes{
  import com.janikibichi.util.AppUtil._

  val httpServerFuture:Future[ServerBinding] = Http().bindAndHandle(routes,http.host,http.port)

  httpServerFuture.onComplete{

    case Success(binding:ServerBinding) =>
      logger.info(s"Akka Server is up and bound to ${binding.localAddress}")

    case Failure(exception) =>
      logger.info(s"Akka http server failed to start with error ${exception.printStackTrace()}")
  }

  StdIn.readLine()

  httpServerFuture
    .flatMap(_.unbind())
    .onComplete(_ => actorSystem.terminate())

}
package com.janikibichi.repository

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, HttpResponse}

import scala.concurrent.Future
import scala.util.{Failure, Success}

object Client{
  import com.janikibichi.util.AppUtil._

  val getDonutsList = "http://localhost:8000/donuts"

  val donutsHttpRequest = HttpRequest(
    uri = getDonutsList,
    method = HttpMethods.GET
  )

  val getDonutsResponse:Future[HttpResponse] = Http().singleRequest(donutsHttpRequest)
  getDonutsResponse.onComplete{
    case Success(donutsResponse) => println(s"Raw HttpResponse=$donutsResponse")
    case Failure(exception) => println(s"Failed to HTTP GET $getDonutsList, error=${exception.getMessage}")
  }

}
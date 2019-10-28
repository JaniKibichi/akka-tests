package com.janikibichi.repository

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, HttpResponse}
import akka.http.scaladsl.unmarshalling.Unmarshal
import com.janikibichi.repository.Messages.Donuts
import com.janikibichi.routes.marshalling.WebJSONSupport
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

object Client extends WebJSONSupport{
  import com.janikibichi.util.AppUtil._

  val getDonutsList = "http://localhost:8000/donuts"

  val donutsHttpRequest = HttpRequest(
    uri = getDonutsList,
    method = HttpMethods.GET
  )

  val getDonutsResponse:Future[HttpResponse] = Http().singleRequest(donutsHttpRequest)
  getDonutsResponse.onComplete{
    case Success(donutsResponse) =>
      println(s"Raw HttpResponse=$donutsResponse")
      val donutsFuture:Future[Donuts] = Unmarshal(donutsResponse).to[Donuts]
      val donuts:Donuts = Await.result(donutsFuture,5.second)
      println(s"Unmarshalled HttpResponse to Case Class =$donuts")

    case Failure(exception) =>
      println(s"Failed to HTTP GET $getDonutsList, error=${exception.getMessage}")
  }

}
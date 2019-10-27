package com.janikibichi.repository

import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import com.janikibichi.repository.Messages.{Donut, Donuts}

import scala.concurrent.Future
import scala.util.Try

object DonutDao{
  import com.janikibichi.util.AppUtil._

  val donutsFromDb:Vector[Donut] = Vector(
    Donut("Plain Donut",1.50),
    Donut("Chocolate Donut",2),
    Donut("Glazed Donut",2.50)
  )

  def fetchDonuts:Future[Donuts] = Future{
    Donuts(donutsFromDb)
  }

  def tryFetchDonuts: Try[HttpResponse] = Try{
    throw new IllegalStateException("Boom!")
  }

  def defaultResponse: HttpResponse = HttpResponse(
    status = StatusCodes.NotFound,
    entity = "An unexpected error occurred, please try again."
  )

  def donutDetails(donutName:String): Future[String] = Future{
    val someDonut = donutsFromDb.find(_.name == donutName)
    someDonut match {
      case Some(donut) => s"$donut"
      case None => s"Donut = $donutName was not found."
    }
  }

}
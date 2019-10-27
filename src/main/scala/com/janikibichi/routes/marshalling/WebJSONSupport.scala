package com.janikibichi.routes.marshalling

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.janikibichi.repository.Messages.{AkkaHttpRestServer, Donut, Donuts}
import spray.json._

trait WebJSONSupport extends SprayJsonSupport with DefaultJsonProtocol{

  implicit val printer:PrettyPrinter = PrettyPrinter

  implicit val serverFormat:RootJsonFormat[AkkaHttpRestServer] = jsonFormat2(AkkaHttpRestServer.apply)
  implicit val donutFormat:RootJsonFormat[Donut] = jsonFormat2(Donut.apply)
  implicit val donutsJsonFormat:RootJsonFormat[Donuts] = jsonFormat1(Donuts.apply)
}
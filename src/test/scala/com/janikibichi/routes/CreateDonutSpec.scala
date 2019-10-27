package com.janikibichi.routes

import akka.http.scaladsl.model.{HttpEntity, HttpMethods, HttpRequest, MediaTypes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.util.ByteString
import org.scalatest.{FlatSpecLike, Matchers}

class CreateDonutSpec extends FlatSpecLike with Matchers with ScalatestRouteTest with Routes {

  "Donut API" should "Create a Valid Donut when posting Json to the route /create-donut path" in{
    val jsonDonutInput = ByteString("""{"name":"plain donut", "price":1.50 }""")

    val httpPostCreateDonut = HttpRequest(
      uri = "http://localhost:8000/create-donut",
      method = HttpMethods.POST,
      entity = HttpEntity(MediaTypes.`application/json`,jsonDonutInput)
    )

    httpPostCreateDonut ~> routes ~> check{
      status.isSuccess() shouldEqual true
      status.intValue() shouldEqual 201
      status.reason() shouldEqual "Created"
    }
  }
}
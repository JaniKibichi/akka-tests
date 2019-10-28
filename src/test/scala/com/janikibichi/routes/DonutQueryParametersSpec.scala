package com.janikibichi.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{FlatSpecLike, Matchers}

class DonutQueryParametersSpec extends FlatSpecLike with Matchers with ScalatestRouteTest with Routes {

  "Donut Query Endpoint" should "match the output got URL /donut/prices?donutName" in {

    Get("/donut/prices?donutName=plain%20donut") ~> routes ~> check {
      status shouldEqual StatusCodes.OK
    }

  }

  it should "fail if the required donutName parameter is not supplied" in {

    Get("/donut/prices?") ~> Route.seal(routes) ~> check{
      responseAs[String] shouldEqual "Request is missing required query parameter 'donutName'"
      status shouldEqual StatusCodes.NotFound
    }

  }

  
}
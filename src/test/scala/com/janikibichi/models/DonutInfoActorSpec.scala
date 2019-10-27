package com.janikibichi.models

import akka.actor.{ActorSystem, Props}
import akka.testkit.{DefaultTimeout, ImplicitSender, TestActorRef, TestKit}
import com.janikibichi.models.DonutStoreProtocol.Info
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers}

import scala.util.Success

class DonutInfoActorSpec extends TestKit(ActorSystem("DonutInfoActorSpec")) with ImplicitSender with DefaultTimeout with FlatSpecLike with BeforeAndAfterAll with Matchers {
  override protected def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  //TESTS
  "DonutInfoActor" should "reply back with true when it receives Tell Pattern Info(vanilla) message" in{
    val donutInfoActor = system.actorOf(Props[DonutInfoActor])

    donutInfoActor ! Info("vanilla")

    expectMsg(true)
  }

  import scala.concurrent.duration._
  it should "respond back within 100 milliseconds" in{
    within(100.millis){
      val donutInfoActor = system.actorOf(Props[DonutInfoActor])

      donutInfoActor ! Info("vanilla")

      expectMsg(true)
    }
  }

  it should "reply back with false when it receives an Ask Pattern Info(plain) message" in{
    import akka.pattern._
    val donutInfoActor = system.actorOf(Props[DonutInfoActor])

    val result  = (donutInfoActor ? Info("plain")).mapTo[Boolean]

    val Success(reply) = result.value.get

    reply shouldBe false

  }

  it should " throw an IllegalStateException when it gets a random message" in{
    val donutInfoActor = TestActorRef[DonutInfoActor]

    val exception = the [IllegalStateException] thrownBy {
      donutInfoActor.receive("Random Donut")
    }

    exception.getClass shouldEqual classOf[java.lang.IllegalStateException]
  }
}
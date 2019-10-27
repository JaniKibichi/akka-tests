package com.janikibichi.models

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, MustMatchers}

import scala.concurrent.duration._

class CounterActorSpec extends TestKit(ActorSystem("counter-actor")) with ImplicitSender with FlatSpecLike with BeforeAndAfterAll with MustMatchers{

  override def afterAll(): Unit = {
    TestKit.shutdownActorSystem(actorSystem = system)
  }

  //TESTS
  "Counter Actor" should "handle GetCount message using TestProbe" in {
    val sender = TestProbe()

    val counterActor = system.actorOf(Props[CounterActor])

    sender.send(counterActor,CounterProtocol.Increment)

    sender.send(counterActor,CounterProtocol.GetCount)

    val state = sender.expectMsgType[Int]

    state must equal(1)
  }

  it should "handle Increment message" in {
    val counterActor = system.actorOf(Props[CounterActor])

    counterActor ! CounterProtocol.Increment

    expectNoMessage(1.seconds)
  }

  it should "handle Decrement message" in {
    val counterActor = system.actorOf(Props[CounterActor])

    counterActor ! CounterProtocol.Decrement

    expectNoMessage(1.seconds)
  }

  it should "handle GetCount message" in {
    val counterActor = system.actorOf(Props[CounterActor])

    counterActor ! CounterProtocol.GetCount

    expectMsg(0)
  }

  it should "handle sequence of messages" in {
    val counterActor = system.actorOf(Props[CounterActor])

    counterActor ! CounterProtocol.Increment
    counterActor ! CounterProtocol.Increment
    counterActor ! CounterProtocol.Decrement
    counterActor ! CounterProtocol.Increment
    counterActor ! CounterProtocol.GetCount

    expectMsg(2)
  }
}
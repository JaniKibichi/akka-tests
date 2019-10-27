package com.janikibichi.models

import akka.actor.ActorSystem
import akka.testkit.{DefaultTimeout, ImplicitSender, TestFSMRef, TestKit}
import com.janikibichi.models.DonutBakingActor.{BakeDonut, BakingData, BakingStates, DonutBakingActor, Start, Stop, StopBaking}
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, MustMatchers}

import scala.concurrent.duration._

class DonutActorFSMSpec extends TestKit(ActorSystem("DonutActorFSM")) with ImplicitSender with DefaultTimeout with FlatSpecLike with BeforeAndAfterAll with MustMatchers{

  private var donutBakingActorFSM: TestFSMRef[BakingStates,BakingData,DonutBakingActor] = _

  override protected def beforeAll(): Unit = {
    donutBakingActorFSM = TestFSMRef(new DonutBakingActor())
  }

  //TESTS
  "DonutBakingActor" should "have an initial state of BakingStates.Stop" in{
    donutBakingActorFSM.stateName must equal(Stop)
  }

  it should "process BakeDonut event and switch to the BakingStates.Start state" in{
    donutBakingActorFSM ! BakeDonut

    //Test the state using awaitCond
    awaitCond(donutBakingActorFSM.stateName == Start, 2.second,1.second)
  }

  it should "process StopBaking event and switch to BakingStates.Stop state" in{
    donutBakingActorFSM ! StopBaking

    //Test the state using awaitCond
    awaitCond(donutBakingActorFSM.stateName ==Stop, 2.second,1.second)
  }

  it should "have a current donut quantity equal to one after the StopBaking event" in{
    donutBakingActorFSM.stateData.qty must equal(1)
  }


  override protected def afterAll(): Unit ={
    TestKit.shutdownActorSystem(system)
  }

}
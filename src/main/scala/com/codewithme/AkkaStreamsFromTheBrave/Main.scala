package com.codewithme.AkkaStreamsFromTheBrave
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.stream.scaladsl.{Flow, Sink, Source}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Random
/**THIS PROGRAM IS ABOUT SCALA LIBRARY FOR AKKA STREAMS
 *
 * REACTIVE STREAM : to process an unbounded amount of data in an async , backpressured way
 * PUBLISHER : An entity that emits aync(single directional) , unbounded amount of data
 * SUBSCRIBER: A sink that receives elements
 * PROCESSOR : In between PUBLISHER AND SUBSCRIBER we have processor which is a transformer that can manipulate the data as it
 *              flows through the reactive stream
 *
 * NOTE : Here everything is async means nothing depends on the other for its action
 *        Reactive streams is a SPI (Service Provider Interface - https://www.reactive-streams.org/) not an API
 *
 * HINTS : backpressured - Can slow down the fast producer when the consumer is very slow
 *
 * AKKA STREAMS : API + Implementation of Reactive Stream*/
object Main  {
  implicit val System = ActorSystem(Behaviors.empty,"DevSystem")
  implicit val ec : ExecutionContext = System.executionContext

  val source = Source(1 to 100)
  val flow =  Flow[Int].map(x => x*2)
  val sink = Sink.foreach[Int](x => println(x))

  /**
   * Defining some components - Sync component - which means it will keep on consuming without any interval
  val firstGraph = source.via(flow).to(sink)
println("Running Akka Stream using sync !!!")
    firstGraph.run()
   }
   */
  def main(args : Array[String]):Unit = {
    println("Running Akka Stream using async !!!")

    val asyncFlow = Flow[Int].mapAsync(4) {
      x =>
        Future {
          Thread.sleep(Random.nextInt(1000))
          x * 2
        }
    }
    val secondGraph = source.via(flow).via(asyncFlow).to(sink)
    secondGraph.run()
  }
}

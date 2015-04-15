package at.linuxhacker.minimalHttpServer

import akka.actor._
import akka.http.Http
import akka.http.Http.ServerBinding
import akka.http.server.Directives._
import akka.http.server.Route
import akka.stream.FlowMaterializer
import akka.pattern.ask
import scala.concurrent.duration._
import akka.util.Timeout

object Test extends App {
  implicit val system = ActorSystem( "akka-http-demo" )
  implicit val executionContext = system.dispatcher
  implicit val materializer = FlowMaterializer()
  implicit val timeout =  Timeout( 1 second )
  
  val firstActor = system.actorOf( Props[FirstActor] )

  lazy val route: Route =
    path( "order" / HexIntNumber ) { id ⇒
      get {
        complete{ ( firstActor ? id ).mapTo[Int].map( s => s"Received GET for order $s" ) }
      } ~
        put {
          complete( s"Received PUT for order $id" )
        }
    }

  val binding: ServerBinding = Http( system ).bind( interface = "localhost", port = 8080 )
  binding.startHandlingWith( route )
}
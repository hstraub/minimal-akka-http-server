package at.linuxhacker.minimalHttpServer

import akka.actor._
import akka.http.Http
import akka.http.Http.ServerBinding
import akka.http.server.Directives._
import akka.http.server.Route
import akka.stream.FlowMaterializer

object Test extends App {
  implicit val system = ActorSystem( "akka-http-demo" )
  implicit val executionContext = system.dispatcher
  implicit val materializer = FlowMaterializer()

  lazy val route: Route =
    path( "order" / HexIntNumber ) { id ⇒
      get {
        complete( s"Received GET for order $id" )
      } ~
        put {
          complete( s"Received PUT for order $id" )
        }
    }

  val binding: ServerBinding = Http( system ).bind( interface = "localhost", port = 8080 )
  binding.startHandlingWith( route )
}
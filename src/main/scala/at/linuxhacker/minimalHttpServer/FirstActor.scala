package at.linuxhacker.minimalHttpServer

import akka.actor.{ Actor, ActorLogging }
import akka.http.server.StandardRoute

class FirstActor extends Actor with ActorLogging {

  def receive = {
    case r: Int => sender ! r 
    
  }
}
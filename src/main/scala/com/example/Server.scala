package com.example

import cats.effect.IO
import com.twitter.finagle.{Service, Http}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Await
import io.finch._
import io.finch.catsEffect._

import com.example.protos.{hello => pb}
import com.example.service._

@SuppressWarnings(Array("org.wartremover.warts.NonUnitStatements"))
object HelloWorldServer extends App with PBSupport {
  private val port = 50051

  val service: Service[Request, Response] = {
    val hello: Endpoint[IO, String] = get("hello") { Ok("Hello, World!") }
    val helloPb: Endpoint[IO, pb.HelloReply] =
      post("pbhello" :: body[pb.HelloRequest, Application.OctetStream]) {
        (req: pb.HelloRequest) =>
          Ok(HelloPbService.reply(req))
      }

    Bootstrap
      .serve[Text.Plain](hello)
      .serve[Application.OctetStream](helloPb)
      .toService
  }

  val server =
    Http.server.serve(s":${port}", service)

  Await.ready(server)
}

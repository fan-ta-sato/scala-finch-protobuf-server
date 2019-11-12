package com.example.service

import com.example.protos.{hello => pb}

object HelloPbService {
  def reply(req: pb.HelloRequest): pb.HelloReply =
    pb.HelloReply(message = s"Hello ${req.name}, welcome proto buffer!")
}

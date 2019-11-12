package com.example

import com.twitter.io.Buf
import com.twitter.util.Try
import io.finch._

import scalapb.{GeneratedMessage, GeneratedMessageCompanion, Message}

trait PBSupport {
  implicit def protobufDecode[T <: GeneratedMessage with Message[T]](
      implicit companion: GeneratedMessageCompanion[T])
    : Decode.Aux[T, Application.OctetStream] =
    Decode.instance((buf, cs) =>
      Try {
        companion.parseFrom(Buf.ByteArray.Owned.extract(buf))
      }.asScala.toEither)

  implicit def protobufEncode[T <: GeneratedMessage]
    : Encode.Aux[T, Application.OctetStream] =
    Encode.instance((res, cs) => Buf.ByteArray.Owned(res.toByteArray))

}

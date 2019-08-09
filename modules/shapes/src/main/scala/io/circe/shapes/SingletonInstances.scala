package io.circe.shapes

import io.circe.{ Decoder, Encoder }
import shapeless.{ Lazy, Typeable, Witness }
import shapeless.syntax.typeable._

import Predef.identity

trait SingletonInstances {
  implicit def encodeSingleton[T: Witness.Aux, W >: T](implicit lEncodeW: Lazy[Encoder[W]]): Encoder[T] =
    lEncodeW.value.contramap(identity)
  implicit def decodeSingleton[T: Witness.Aux: Typeable, W >: T](implicit lDecodeW: Lazy[Decoder[W]]): Decoder[T] =
    lDecodeW.value.emap(_.narrowTo[T].toRight("Singleton type narrowing mismatch"))
}

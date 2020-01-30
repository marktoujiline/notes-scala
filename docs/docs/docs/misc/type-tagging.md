---
layout: docs
title: Type Tagging
permalink: docs/type-tagging/
---
# Type Tagging

The same type can have multiple purposes. A `Long` can represent a user id or a transaction id. One solution is with case class wrappers:
```scala
case class TransactionId(id: Long)
case class UserId(id: Long)

val tId: TransactionId = TractionId(1)
val uId: UserId = UserId(1)
```

Another solution is to use __type tagging__ to differentiate between values of the same type. It is essentially a union of a type and an empty trait:
```scala
// implementation
type @@[A, B] = A with B

implicit class TaggingExtensions[A](val a: A) extends AnyVal {
  @inline def taggedWith[B]: A @@ B = a.asInstanceOf[A @@ B]

  /** Synonym operator for `taggedWith`. */
  @inline def @@[B]: A @@ B = taggedWith[B]
}

// usage
trait TransactionId
trait UserId

val tId: Long @@ TransactionId = 1.@@[TransactionId]
val uId: Long @@ UserId = 1.@@[UserId]
```
You will usually just use a library implementation though.

## Benefits
- Tagged type is subtype of base type
```scala
def print(s: String): Unit = println(s)

trait UID
print("hello".@@[UID])

"hello".@@[UID] == "hello" // true
```

- Performance is better than case class approach since you don't need to box values

## Downsides
- Case class can be extended with methods
```scala
case class TransactionId(id: Long) {
    def encrypt = ???
    def decrypt = ???
}
```

## Future
In Scala 3, this will be replaced by Opaque types

### Links
https://medium.com/iterators/to-tag-a-type-88dc344bb66c
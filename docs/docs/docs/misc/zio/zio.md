---
layout: docs
title: Type Tagging
permalink: docs/type-tagging/
---

# ZIO
A Scala library for typesafe asynchronous programming.

## Types
```scala
// type ZIO[R, E, A]
type UIO[A] = ZIO[Any, Nothing, A] // Effect with no requirements, can't fail, and succeeds with A
type URIO[R, A] = ZIO[R, Nothing, A] // Effect with requirement, can't fail, and succeeds with A
type Task[A] = ZIO[Any, Throwable, A] // Effect with no requirements, returns Either[Trowable, A]
type RIO[R, A] = ZIO[R, Throwable, A] // Effect with requirement, returns Either[Throwable, A]
type IO[E, A] = ZIO[Any, E, A] // Effect with no requirement, returns Either[E, A], E being more broad than throwable.
```

Each type has a companion object.
- Future corresponds most closely to Task
- Cats Effects corresponds to RIO
- UIO is usefull for describing infallible effects
- Use ZIO if you know what you're doing.


### Fiber
__Green thread__: a thread that is scheduled by runtime library or VM instead of at OS level. Also known as user level threads.
__Fiber__: a fiber is a lightweight thread that uses cooperative multitasking. Each fiber must yield to allow another fiber to run.
- Consumes little memory
- Has flexible stask
- Doesn't block
- Garbage collected automatically

All zio effects are executed inside a fiber.

```scala
Fiber[E, A]
// E: Failure type
// A: Success type
```

No R type parameter because they model effects that are already running and which already had environment provided to them.

You can `fork` any `IO[E, A]` to immediately yield an `UIO[Fiber[E, A]]`
```scala
val a = ZIO { 5 } // Task[Int]
a.fork // ZIO[Any, Nothing, Fiber[Throwable, Int]]

val b = UIO { 5 } // UIO[Int]
b.fork // ZIO[Any, Nothing, Fiber[Nothing, Int]]

val c = IO { 2 }
val d = IO { 3 }

val res = for {
    cRes <- c.fork
    dRes <- d.fork
    v1 <- cRes.join
    v2 <- dRes.join
} yield v1 + v2 // ZIO[Any, Throwable, Int]

val runtime = new DefaultRuntime {}
runtime.unsafeRun(res) // 5
```
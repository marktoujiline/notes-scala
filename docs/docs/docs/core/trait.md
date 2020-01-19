---
layout: docs
title: Trait
permalink: docs/trait/
---
# Trait

A trait shares data and interfaces between classes.

```scala
trait Animal       // no data or interfaces
trait Animal {     // data
    val n = 5
}
trait Animal {
    def n: Int     // interface
}
trait Animal {     // data and interface
    def n: Int
    val defTwo = 5
}
trait Animal {
    println("Roar") // will get executed when object with this trait is instantiated
}
```
You can use def or val for abstract members, but they have different behaviors. Lazy val cannot be used as abstract member, but can be used to implement one.
```scala
trait Animal {
    def a: Int
    val b: Int
}

new Animal {  // good
    val a = 5
    val b = 6
}

new Animal {  // good
    lazy val a = 5
    lazy val b = 6
  }

new Animal {  // error
    def a = 5
    def b = 6     // method b needs to be a stable, immutable value
}
```
A trait can be generic.
```scala
trait Iterator[A] {
  def hasNext: Boolean
  def next(): A
}
```
Using traits.
```scala
// create object that implements trait
val intIterator = new Iterator[Int] {
    ...
}

// create class that implements trait
class IntIterator extends Iterator[Int] {
    ...
}
val intIterator = new IntIterator();

// create object that implements trait
object IntIterator extends Iterator[Int] {
    ...
}
```
Traits can extends other traits.
```scala 
trait A
trait B extends A
```
Classes and traits can extends more than one trait.
```scala
trait A
trait B
trait C extends A with B
```
You can limit classes/traits that can extend it.
```scala
trait A
trait B {
    this: A => 
    ... // can access this, ex: this.getClass()
}
class C extends A
class D extends B         // compile error because to extend B, you must extend A
class D extends B with A  // ok
class E extends C with B  // ok
```
Traits are constructed in order from left to right, before the class is constructed.
```scala
trait A { println("trait A is constructed") }
trait B { println("trait B is constructed") }
trait C { println("trait C is constructed") }
class D extends A with B with C {
    println("class D is constructed")
}
val d = new D
//trait A is constructed
//trait B is constructed
//trait C is constructed
//class D is constructed
```
When multiple traits are mixed into a class and they have the same method name, when that method is invoked, the last trait that’s created — the one on the farthest right — is the one that’s called.


## Questions?
1. How are Scala traits and Java8 interfaces different?

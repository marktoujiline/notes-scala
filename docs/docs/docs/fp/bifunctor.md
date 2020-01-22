---
layout: docs
title: Bifunctor
permalink: docs/bifunctor/
---
# Bifunctor
## Overview
A functor maps objects and morphisms from one category to another while preserving composition and identity, F: A -> B.

Similar to a regular function, a functor can also map 2 categories to a single category, F: A x B -> C. This is known as a __bifunctor__ whose composition is called _bimap_.

A bifunctor is also a valid functor for both source categories, F: A -> C, F: B -> C. We can prove this by setting the alternative morphism to identity, which essentially disregards one of the arguments.

Another way of thinking about this is you can extract two functors from the bifunctor since you can define two fmap functions: leftFmap and rightFmap.

Identity is (__id__, __id__).

## Categorical View
{% graphviz %}
digraph {

    subgraph cluster_2 {
        style="rounded"
        label="F";
        "F(a, c)" -> "F(b, d)" [label="F(f, g)"];
    }
    subgraph cluster_0 {
        style="rounded"
        label="A";
        a -> b [label=f];
        a -> "F(a, c)";
        b -> "F(b, d)";
    }

    subgraph cluster_1 {
        style="rounded"
        label="B";
        c -> d [label=g];
        c -> "F(a, c)";
        d -> "F(b, d)";
    }
}
{% endgraphviz %}
## Programming View
```scala
// type variable F is the bifunctor
trait Bifunctor[F[_, _]] {
    def bimap[A, B, C, D](f: A => B, g: C => D): F[A, C] => F[B, D]

    def leftFMap[A, B](f: A => B): F[A, Any] => F[B, Any] = bimap(f, identity)

    def rightFMap[C, D](g: C => D): F[Any, C] => F[Any, D] = bimap(identity, g)
}

val eitherBifunctor = new Bifunctor[Either] {
    def bimap[A, B, C, D](f: A => B, g: C => D): Either[A, C] => Either[B, D] = (eitherAorC: Either[A, C]) => eitherAorC match {
        case Left(a) => Left(f(a))
        case Right(b) => Right(g(b))
    }
}
eitherBifunctor.bimap((x: List[Int]) => x.head, (y: Int) => y + 5)(Left(List(5, 6, 7)))
// Either[Int, Int] = Left(5)
eitherBifunctor.bimap((x: List[Int]) => x.head, (y: Int) => y + 5)(Right(7))
// Either[Int, Int] = Right(12)
eitherBifunctor.leftFMap((x: Int) => x + 5)(Left(10))
// Either[Int, Any] = Left(15)
eitherBifunctor.rightFMap((x: Int) => x + 5)(Right(10))
// Either[Any, Int] = Right(15)
```
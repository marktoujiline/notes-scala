---
layout: docs
title: Bifunctor
permalink: docs/bifunctor/
---
# Bifunctor
## Overview
A bifunctor is a functor whose domain is the product category. It is a functor for both arguments. This can be proven by setting either side to be constant.

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
trait Bifunctor[F[_, _]] {
    def bimap[A, B, C, D](f: A => B, g: C => D): F[A, C] => F[B, D]
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
```
---
layout: docs
title: Case Class
permalink: docs/case-class/
---

# Case Class

## Runtime Parameter Requirements
```scala
case class UserId(id: Long) {
    require(id > 0)
}

UserId(5)
UserId(-1) // IllegalArgumentException: requirement failed
```


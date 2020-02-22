# Sbt

Eviction: 
When a class is needed during execution, the JVM classloader loads the first matching class file from the classpath (any other matching class files are ignored). Because of this, having multiple versions of the same library in the classpath is generally undesirable:

Need to fetch and bundle multiple library versions when only one is actually used
Unexpected runtime behavior if the order of class files changes
Therefore, build tools like sbt and Gradle will pick one version and evict the rest when resolving JARs to use for compilation and packaging. By default they pick the latest version of each library, but it is possible to specify another version if required.

https://docs.scala-lang.org/overviews/core/binary-compatibility-for-library-authors.html

## See dependency tree
- https://github.com/jrudolph/sbt-dependency-graph
```scala
whatDependsOn com.typesafe.play play-server_2.11 2.5.19
```

import Dependencies._

ThisBuild / scalaVersion     := "2.12.9"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val micrositeSettings: Seq[Def.Setting[_]] = Seq(
      micrositeName := "Mark Toujiline's Scala Notes",
      micrositeDescription := "All personal notes related to Scala",
      micrositeBaseUrl := "notes-scala",
      micrositeDocumentationUrl := "docs",
      micrositeGithubOwner := "marktoujiline",
      micrositeGithubRepo := "notes-scala",
      micrositePushSiteWith := GitHub4s,
      micrositeGithubToken := sys.env.get("NOTES_SCALA_GH"),
      includeFilter in makeSite := "*.html" | "*.css" | "*.png" | "*.jpg" | "*.gif" | "*.js" | "*.swf" | "*.md" | "*.svg"
    )


lazy val docs = (project in file("docs"))
  .settings(moduleName := "docs")
  .settings(micrositeSettings: _*)
  .enablePlugins(MicrositesPlugin)

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.


lazy val projectSettings = Seq(
  organization := "birchmd",
  scalaVersion := "2.13.2",
  version := "0.1.0-SNAPSHOT",
  Compile / run / fork := true, 
  scalafmtOnCompile := true
)

lazy val compilerSettings = CompilerSettings.options

lazy val trianglePegSolitaire = (project in file("."))
  .settings(projectSettings: _*)
  .settings(CompilerSettings.options)
  .enablePlugins(JavaAppPackaging)
  .settings(
    libraryDependencies += "org.typelevel"  %% "cats-core"  % "2.0.0"
  )

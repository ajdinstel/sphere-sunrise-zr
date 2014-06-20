name := "sphere-sunrise"

organization := "io.sphere"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava).configs(IntegrationTest)

scalaVersion := "2.10.4"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies ++= Seq(
  "io.sphere.jvmsdk" %% "play-sdk" % "1.0.0-20140606-SNAPSHOT"
)

initialCommands in console := "import tutorial.ClientFactory.createClient;" +
  "import io.sphere.sdk.categories._;" +
  """println("Use 'val client = createClient()' to create a client instance.")"""

javaUnidocSettings
import sbt._

object Dependencies {

  val slf4jVersion = "1.6.4"
  val slf4jNop = "org.slf4j" % "slf4j-nop" % slf4jVersion

  val commonDependencies: Seq[ModuleID] = Seq(
    "ch.qos.logback" % "logback-classic" % "1.1.3",
    "org.slf4j" % "log4j-over-slf4j" % "1.7.12",
    "org.scalatest" %% "scalatest" % "2.2.4" % "test",
    slf4jNop,
    "org.threeten" % "threetenbp" % "1.3",
    "junit" % "junit" % "4.12" % "test"
  )

  val json: Seq[ModuleID] = Seq(
    "io.argonaut" %% "argonaut" % "6.2",
    "com.propensive" %% "rapture-json-argonaut" % "1.1.0",
    "com.typesafe.play" %% "play-json" % "2.6.7")

  val solr: Seq[ModuleID] = Seq("io.ino" %% "solrs" % "2.1.0")
  val mongo: Seq[ModuleID] = Seq("org.reactivemongo" %% "play2-reactivemongo" % "0.13.0-play26")

  val akkaVersion = "2.4.2"
  val kafkaVersion = "0.10.1.0"
  val jsonVersion = "3.4.2"

  val kafka: Seq[ModuleID] = Seq(
  "org.apache.kafka" % "kafka-clients" % kafkaVersion,
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe" % "config" % "1.3.0",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
  "org.json4s" %% "json4s-native" % jsonVersion,
  "org.json4s" %% "json4s-ext" % jsonVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion % Test,
  "org.mockito" % "mockito-core" % "1.9.5" % Test,
  "org.scalatest" %% "scalatest" % "2.2.4" % Test,
  "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0" % Test,
  "ch.qos.logback" % "logback-classic" % "1.1.7" % Test
  )

  val crudDependencies: Seq[ModuleID] = commonDependencies ++ json ++ {
    Seq(
      "io.swagger"             %% "swagger-play2"       % "1.6.0",
      "org.webjars"            %  "swagger-ui"          % "3.2.2",
      "org.scalatestplus.play" %% "scalatestplus-play"  % "3.1.1" % Test
    )
  }

  val etlDependencies: Seq[ModuleID] = commonDependencies ++ json ++ {
    Seq(
      "com.typesafe.play" %% "anorm" % "2.5.2",
      "com.facebook.presto" % "presto-jdbc" % "0.180",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
      "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.1" % Test,
      "org.syslog4j" % "syslog4j" % "0.9.46" % Runtime
    )
  }
}

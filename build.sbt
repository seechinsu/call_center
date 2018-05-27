import java.util.Calendar

name := MyBuild.NamePrefix + "root"

version := "0.0.1"

scalaVersion := "2.11.8"

lazy val common = project.
  settings(Common.settings: _*).
  settings(libraryDependencies ++= Dependencies.json).
  settings(libraryDependencies ++= Dependencies.mongo).
  settings(libraryDependencies ++= Dependencies.solr).
  settings(libraryDependencies ++= Seq(
    guice))

lazy val kafka = (project in file("kafka")).
  settings(Common.settings: _*).
  settings(libraryDependencies ++= Dependencies.kafka).
  settings(libraryDependencies ++= Seq(
    guice))

lazy val case_api = (project in file("case-api")).
  dependsOn(common, kafka).
  settings(Common.settings: _*).
  settings(routesImport += "play.modules.reactivemongo.PathBindables._").
  settings(libraryDependencies ++= Dependencies.crudDependencies).
  settings(libraryDependencies ++= Seq(
    guice,
    ws,
    javaWs,
    specs2 % Test)).
    settings(
        Seq(
            dockerRepository := Some("call.center"),
            //dockerBaseImage := "openjdk:8-jdk-alpine",
            packageName in Docker := name.value,
            dockerUpdateLatest := true,
            version in Docker := "latest",
            defaultLinuxInstallLocation in Docker := s"/opt/${name.value}",
            dockerEntrypoint := Seq("bin/%s" format executableScriptName.value, "-Dconfig.resource=docker.conf")
        )
    ).
  enablePlugins(PlayScala, DockerPlugin)

lazy val case_search = (project in file("case-search")).
  dependsOn(common).
  settings(Common.settings: _*).
  settings(libraryDependencies ++= Dependencies.crudDependencies).
  settings(libraryDependencies ++= Seq(
    ws,
    javaWs,
    specs2 % Test)).
  settings(
    buildInfoKeys := Seq[BuildInfoKey](
      name,
      version,
      BuildInfoKey.action("buildTime")(Calendar.getInstance().getTime),
      BuildInfoKey.action("commitHash")(sys.env.getOrElse("GIT_COMMIT","UNKNOWN"))),
    buildInfoPackage := "call.center"
  ).
  enablePlugins(PlayScala)


lazy val case_worker = (project in file("case-worker")).
  dependsOn(common, kafka).
  settings(Common.settings: _*).
  settings(libraryDependencies ++= Seq(
    ws,
    javaWs,
    specs2 % Test)).
  settings(
      buildInfoKeys := Seq[BuildInfoKey](
          name,
          version,
          BuildInfoKey.action("buildTime")(Calendar.getInstance().getTime),
          BuildInfoKey.action("commitHash")(sys.env.getOrElse("GIT_COMMIT","UNKNOWN"))),
      buildInfoPackage := "call.center.worker"
  ).
  enablePlugins(PlayScala, BuildInfoPlugin)

lazy val case_etl = (project in file("case-etl")).
  dependsOn(common, kafka).
  settings(Common.settings: _*).
  settings(libraryDependencies ++= Dependencies.crudDependencies).
  settings(libraryDependencies ++= Dependencies.etlDependencies).
  settings(libraryDependencies ++= Seq(
    ws,
    javaWs,
    jdbc,
    evolutions,
    specs2 % Test)).
  enablePlugins(PlayScala, BuildInfoPlugin)

lazy val root = (project in file(".")).
  aggregate(case_api, case_search, case_worker, case_etl)
  .settings(
      run := {
          (run in case_api in Compile).evaluated
      }
  )

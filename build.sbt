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

lazy val caseApi = (project in file("case-api")).
  dependsOn(common, kafka).
  settings(Common.settings: _*).
  settings(routesImport += "play.modules.reactivemongo.PathBindables._").
  settings(libraryDependencies ++= Dependencies.crudDependencies).
  settings(libraryDependencies ++= Seq(
    guice,
    ws,
    javaWs,
    specs2 % Test)).
  enablePlugins(PlayScala)

lazy val caseSearch = (project in file("case-search")).
  dependsOn(common).
  settings(Common.settings: _*).
  settings(libraryDependencies ++= Dependencies.crudDependencies).
  settings(libraryDependencies ++= Seq(
    ws,
    javaWs,
    specs2 % Test)).
  enablePlugins(PlayScala)


lazy val worker = (project in file("case-worker")).
  dependsOn(common, kafka).
  settings(Common.settings: _*).
  settings(libraryDependencies ++= Seq(
    ws,
    javaWs,
    specs2 % Test)).
  enablePlugins(PlayScala, BuildInfoPlugin)

lazy val root = (project in file(".")).
  aggregate(caseApi, caseSearch, worker)
  .settings(
      run := {
          (run in caseSearch in Compile).evaluated
      }
  )

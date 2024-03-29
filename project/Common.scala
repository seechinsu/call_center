import sbt.Keys._
import sbt._

object Common {
  val appVersion = "0.0.1"

    lazy val copyDependencies = TaskKey[Unit]("copy-dependencies")

    def copyDepTask = copyDependencies := (update, crossTarget, scalaVersion) map {
      (updateReport, out, scalaVer) =>
      updateReport.allFiles foreach { srcPath =>
        val destPath = out / "lib" / srcPath.getName
        IO.copyFile(srcPath, destPath, preserveLastModified=true)
      }
    }

    val settings: Seq[Def.Setting[_]] = Seq(
      version := appVersion,
      scalaVersion := "2.11.8",
      javacOptions ++= Seq("-source", "1.8", "-target", "1.8"), //, "-Xmx2G"),
      scalacOptions ++= Seq("-deprecation", "-unchecked"),
      resolvers += Opts.resolver.mavenLocalFile,
      copyDepTask,
      resolvers += Resolver.bintrayRepo("cakesolutions", "maven"),
      resolvers ++= Seq(DefaultMavenRepository,
        Resolver.defaultLocal,
        Resolver.mavenLocal,
        "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
        "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository",
        "Apache Staging" at "https://repository.apache.org/content/repositories/staging/",
        Classpaths.typesafeReleases,
        "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
        "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
        "Java.net Maven2 Repository" at "http://download.java.net/maven/2/",
        Classpaths.sbtPluginReleases,
        "Eclipse repositories" at "https://repo.eclipse.org/service/local/repositories/egit-releases/content/")
    )
//  val settings: Seq[Def.Setting[_]] = Seq(
//    version := appVersion,
//    scalaVersion := "2.11.8",
//    javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xmx2G"),
//    scalacOptions ++= Seq(
//      "-deprecation",
//      "-unchecked",
//      "-Xfatal-warnings",
//      "-feature",
//      "-language:_"
//    )
//    ,
//    resolvers ++= Seq(
//      DefaultMavenRepository,
//      Resolver.defaultLocal,
//      Resolver.mavenLocal,
//      Resolver.sonatypeRepo("releases"),
//      Resolver.sonatypeRepo("snapshots"),
//      Resolver.typesafeRepo("releases"),
//      Resolver.bintrayRepo("websudos", "oss-releases"),
//      Resolver.bintrayRepo("outworkers", "oss-releases"),
//      Resolver.bintrayRepo("cakesolutions", "maven"),
//      Resolver.bintrayRepo("hseeberger", "maven")
//    ) ++ repositories
//  )
//
//  val repositories = Seq(
//    "confluent" at "http://packages.confluent.io/maven/",
//    Resolver.sonatypeRepo("public")
//  )
}

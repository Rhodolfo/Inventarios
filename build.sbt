val myScalaVersion = "2.11.8"
val myVersion = "1.0"
val mySparkVersion = "2.2.0"

lazy val commonSettings = Seq(
   organization := "org.arena",
   version := myVersion,
   scalacOptions ++= Seq("-unchecked", "-deprecation"),
   scalaVersion := myScalaVersion)


libraryDependencies ++= Seq( "org.apache.spark" %% "spark-sql" % mySparkVersion,
                            "org.apache.spark" %% "spark-core" % mySparkVersion,
                             "org.scalafx" %% "scalafx" % "8.0.144-R12")

lazy val herramienta = (project in file("."))
     .settings(commonSettings: _*)
     .settings(name := "Herramineta Inventarios")

/*
assemblyMergeStrategy in assembly := {
     case PathList("META-INF", xs @ _ *)  => MergeStrategy.discard
     case x                             => MergeStrategy.first
}*/

assemblyMergeStrategy in assembly := { 
case PathList("META-INF", xs @ _*) =>
    (xs map {_.toLowerCase}) match {
      // ... possibly other settings ...
      case "services" :: xs =>
        MergeStrategy.filterDistinctLines
      case _ => MergeStrategy.discard
  }
  case _ => MergeStrategy.first
}

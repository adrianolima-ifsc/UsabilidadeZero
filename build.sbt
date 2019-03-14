name := """usabilidade"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.12.4"

// Compile the project before generating Eclipse files, so that generated .scala or .class files for views and routes are present
EclipseKeys.preTasks := Seq(compile in Compile)
EclipseKeys.projectFlavor := EclipseProjectFlavor.Java
EclipseKeys.createSrc := EclipseCreateSrc.ValueSet(EclipseCreateSrc.ManagedClasses, EclipseCreateSrc.ManagedResources)

// Resolver is needed only for SNAPSHOT versions
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

libraryDependencies ++= Seq(
	guice,
	"mysql" % "mysql-connector-java" % "8.0.11",
	"org.webjars" % "bootstrap" % "3.3.6",
	"com.adrianhurt" %% "play-bootstrap" % "1.4-P26-B4-SNAPSHOT",
	"com.typesafe.play" %% "play-mailer" % "6.0.1",
	"com.typesafe.play" %% "play-mailer-guice" % "6.0.1"
)

playEnhancerEnabled := false
sbtPlugin := true

name := "sbt-sound"

organization := "com.github.masseguillaume"

version := "1.0.7"

crossSbtVersions := Seq("0.13.17", "1.1.2")

scmInfo := Some(ScmInfo(
  browseUrl = url("https://github.com/MasseGuillaume/sbt-sound"),
  connection = "scm:git:git@github.com:MasseGuillaume/sbt-sound.git"
))

licenses := Seq("Apache 2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))

developers ++= List(
  Developer(
    "orrsella",
    "Orr Sella",
    "orr@sella.org",
    url("http://orrsella.com")
  )
)


publishMavenStyle := false
bintrayRepository := "sbt-plugins"
bintrayOrganization := None
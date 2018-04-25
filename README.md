# sbt-sound

project/plugins.sbt

```scala
resolvers += Resolver.url("masseguillaume", url("https://dl.bintray.com/masseguillaume/sbt-plugins"))(Resolver.ivyStylePatterns)
addSbtPlugin("com.github.masseguillaume" % "sbt-sound" % "1.0.6")
```

build.sbt

```scala
sound.play(task, success = Sounds.Glass, failure = Sounds.Basso)
```

* Basso
* Blow
* Bottle
* Frog
* Funk
* Glass
* Hero
* Morse
* Ping
* Pop
* Purr
* Sosumi
* Submarine
* Tink


### By default

```
compile in Compile
compile in Test
test in Test
```
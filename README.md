# sbt-sound

Enable sbt-sound as a global plugin:

`~/.sbt/1.0/plugins/plugins.sbt` or `~/.sbt/0.13/plugins/plugins.sbt`

```scala
addSbtPlugin("com.github.masseguillaume" % "sbt-sound" % "1.0.7")
```

It will enable

```
compile in Compile
compile in Test
test in Test
```

If you want to customize, enable sbt-sound as a project plugin:

project/plugins.sbt

Same as above

build.sbt

```scala
sound.play(compile / Compile, success = Sounds.Glass, failure = Sounds.Basso)
```

Sounds:

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

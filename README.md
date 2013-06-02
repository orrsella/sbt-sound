# sbt-sound

An [sbt](http://www.scala-sbt.org/) (Simple Build Tool) plugin for adding sounds to sbt's task completions.

This plugin allows you to associate sounds with successful and/or failed task completions, giving you audio feedback. Any TaskKey can have a sound association. This is especially useful for:

* Long build times
* A failing `~compile` that is running in the background/behind other windows
* A failing `~test`
* Drawing your attention to any specific task outcome

The reason I wrote this plugin is that I usually code with a running sbt `~compile` in the background, and would like to know asap when something brakes. I code in [Sublime Text](http://www.sublimetext.com/) (see [my plugin for it](https://github.com/orrsella/sbt-sublime)), and when I'm not connected to an external monitor I have sbt in the background, periodically checking it to see everything's fine. This plugin solves this problem for me, only drawing my attention when the build breaks. I think others could find this plugin useful in other ways as well.

## Add Plugin

To add sbt-sound functionality to your project add the following to your `project/plugins.sbt` file:

```scala
addSbtPlugin("com.orrsella" % "sbt-sound" % "1.0.1")
```

If you want to use it for more than one project, you can add it to your global plugins file, usually found at: `~/.sbt/plugins/plugins.sbt` and then have it available for all sbt projects. See [Using Plugins](http://www.scala-sbt.org/release/docs/Getting-Started/Using-Plugins.html) for additional information on sbt plugins.

### Requirements

* sbt 0.12.x
* Scala 2.9.x, 2.10.x

### Troubleshooting

If you added the plugin globally but still don't have it available, try:

```
$ sbt
> reload plugins
> clean
> reload return
```

Essentially, this enters the `project` project, cleans it, and returns back to your main project (remember that [sbt is recursive](http://www.scala-sbt.org/release/docs/Getting-Started/Full-Def.html#sbt-is-recursive) – each `project/` folder is an sbt project in itself!).

## Usage

### Sounds

The sbt-sound jar comes pre-loaded with 14 sounds (shamelessly copied from Mountain Lion sounds folder, hope I won't get in trouble for this):

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

You can also specify any `.wav` or `.aiff` file on your local machine (maybe other formats work, these are the ones I tested with).

### Configuration

After enabling the plugin as detailed above, you can configure it by adding any of the following to `build.sbt`:

```scala
sound.play(compile in Compile, Sounds.Basso) // play the 'Basso' sound whenever compile completes (successful or not)

sound.play(compile in Compile, Sounds.None, Sounds.Pop) // play the 'Pop' sound only when compile fails

sound.play(test in Test, Sounds.Purr, "/Users/me/Sounds/my-sound.wav") // play 'Purr' when test completes successfully, and
                                                                       // play the local 'my-sound' wav file when it fails
```

You can configure any TaskKey in the above way

### Stacking

The way sbt-sound works is by adding `mapR` to every task's completion. Because the functionality is tucked on every task, running tasks that depend on other tasks (and execute them), will cause all sounds in the chain to play. So if for example you specify a sound for `compile` and `test`, and when running `test` the code compiles because of the `compile` command, both sounds will play. Take that into consideration when configuration which tasks to play sounds for.

## Feedback

Any comments/suggestions? Let me know what you think – I'd love to hear from you. Send pull requests, issues or contact me: [@orrsella](http://twitter.com/orrsella) and [orrsella.com](http://orrsella.com)

## License

This software is licensed under the Apache 2 license, quoted below.

Copyright (c) 2013 Orr Sella

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
# sbt-sound

An [sbt](http://www.scala-sbt.org/) (Simple Build Tool) plugin for adding sounds to sbt's task completions.

This plugin allows you to associate sounds with successful and/or failed task completions, giving you audio feedback. Any `TaskKey` can have a sound association. This is especially useful for:

* Long build times
* A failing `~compile` that is running in the background/behind other windows
* A failing `~test`
* Drawing your attention to any specific task outcome

The reason I wrote this plugin is that I usually code with a running sbt `~compile` in the background, and would like to know asap when something breaks. I code in [Sublime Text](http://www.sublimetext.com/) (see [my plugin for it](https://github.com/orrsella/sbt-sublime)), and when I'm not connected to an external monitor I have sbt in the background, periodically checking it to see everything's fine. This plugin solves this problem for me, only drawing my attention when the build breaks. I think others could find this plugin useful in other ways as well.

## Add Plugin

To add sbt-sound functionality to your project add the following to your `project/plugins.sbt` file:

```scala
addSbtPlugin("com.orrsella" % "sbt-sound" % "1.0.5")

// For sbt 0.12.x, 0.13.x:
addSbtPlugin("com.orrsella" % "sbt-sound" % "1.0.4")
```

## Usage

### Sounds

The sbt-sound jar comes pre-loaded with 14 sounds (shamelessly copied from Mountain Lion's sounds folder, hope I won't get in trouble for this):

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

You can also specify any `.wav` or `.aiff` file on your local machine (maybe other formats work as well, these are the ones I tested with).

### Configuration

#### build.sbt

After enabling the plugin, you can configure it by adding any of the following to `build.sbt`:

```scala
import SbtSound._

sound.play(compile in Compile, Sounds.Basso) // play the 'Basso' sound whenever compile completes (successful or not)

sound.play(compile in Compile, Sounds.None, Sounds.Pop) // play the 'Pop' sound only when compile fails

sound.play(test in Test, Sounds.Purr, "/Users/me/Sounds/my-sound.wav") // play 'Purr' when test completes successfully
                                                                       // or the wav file 'my-sound' when it fails
```

You can configure any sbt `TaskKey` (and as many as you like) in the above way.

#### Build.scala

If you're using a `.scala` build file, you can add the following:

```scala
import com.orrsella.sbtsound.SbtSound._
...

object MyBuild extends Build {
  lazy val main = Project (
    "my-proj",
    file("."),
    settings =
      Defaults.defaultSettings ++
      ... ++
      sound.play(installDevice in AndroidKeys.Android, "/Users/mark/Documents/Quatsch/Hoo.wav")
  )
}
```

Above example curtsey of [@i-am-the-slime](https://github.com/i-am-the-slime) – it alerts when an entire build is complete (using the [sbt android-plugin](https://github.com/jberkel/android-plugin) to also install on the device). Example full project definition can be found [here](https://gist.github.com/i-am-the-slime/fc207e61d50e29fe2837/#comment-852708).

### Stacking

The way sbt-sound works is by adding `task <<= (task) mapR { ... }` to every task's completion. Because the functionality is tucked on every task you configure in `build.sbt`, running tasks that depend on other tasks (and execute them), will cause all sounds in the chain to play. So if, for example, you specify a sound for `compile` and `test`, and when running `test` the code compiles because of the `compile` command, both sounds will play. Take that into consideration when configuration which tasks to play sounds for. The easiest way to avoid this problem is to define sounds for failing actions, which usually don't trigger the following tasks, thus solving this "problem".

## Feedback

Any comments/suggestions? Let me know what you think – I'd love to hear from you. Send pull requests, issues or contact me: [@orrsella](http://twitter.com/orrsella) and [orrsella.com](http://orrsella.com)

## License

This software is licensed under the Apache 2 license, quoted below.

Copyright (c) 2013 Orr Sella

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

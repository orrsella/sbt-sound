package com.orrsella.sbtsound

import java.io.{InputStream, FileInputStream}
import sbt._
import sun.audio.{AudioStream, AudioPlayer}

object SbtSound extends AutoPlugin {
  abstract class Sound {
    def play()
    def play(input: InputStream) {
      AudioPlayer.player.start(new AudioStream(input))
    }
  }

  case class FileSound(path: String) extends Sound {
    def play() {
      play(new FileInputStream(path))
    }
  }

  case class ResourceSound(res: String) extends Sound {
    def play() {
      play(getClass.getResourceAsStream(res))
    }
  }

  object Sounds {
    object None       extends FileSound("")
    object Basso      extends ResourceSound("/Basso.aiff")
    object Blow       extends ResourceSound("/Blow.aiff")
    object Bottle     extends ResourceSound("/Bottle.aiff")
    object Frog       extends ResourceSound("/Frog.aiff")
    object Funk       extends ResourceSound("/Funk.aiff")
    object Glass      extends ResourceSound("/Glass.aiff")
    object Hero       extends ResourceSound("/Hero.aiff")
    object Morse      extends ResourceSound("/Morse.aiff")
    object Ping       extends ResourceSound("/Ping.aiff")
    object Pop        extends ResourceSound("/Pop.aiff")
    object Purr       extends ResourceSound("/Purr.aiff")
    object Sosumi     extends ResourceSound("/Sosumi.aiff")
    object Submarine  extends ResourceSound("/Submarine.aiff")
    object Tink       extends ResourceSound("/Tink.aiff")
  }

  object sound {
    val default = Sounds.Glass

    def play[T](t: TaskKey[T]): Setting[Task[T]] = play(t, default, default)
    def play[T](t: TaskKey[T], s: String): Setting[Task[T]] = play(t, FileSound(s), FileSound(s))
    def play[T](t: TaskKey[T], s: Sound): Setting[Task[T]] = play(t, s, s)
    def play[T](t: TaskKey[T], suc: String, fail: Sound): Setting[Task[T]] = play(t, FileSound(suc), fail)
    def play[T](t: TaskKey[T], suc: Sound, fail: String): Setting[Task[T]] = play(t, suc, FileSound(fail))
    def play[T](t: TaskKey[T], suc: String, fail: String): Setting[Task[T]] = play(t, FileSound(suc), FileSound(fail))

    def play[T](t: TaskKey[T], suc: Sound, fail: Sound): Setting[Task[T]] = {
      t := {
        t.result.value match {
          case Inc(inc: Incomplete) =>
            if (fail != Sounds.None) fail.play()
            throw inc
          case Value(value) =>
            if (suc != Sounds.None) suc.play()
            value
        }
      }
    }
  }
}

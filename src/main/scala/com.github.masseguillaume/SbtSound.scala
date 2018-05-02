package com.github.masseguillaume

import java.io.{InputStream, FileInputStream}
import sun.audio.{AudioStream, AudioPlayer}

import sbt._
import sbt.Keys._
import sbt.plugins.JvmPlugin

object SbtSound extends AutoPlugin {
  override def trigger: PluginTrigger = allRequirements
  override def requires: Plugins = JvmPlugin
  override lazy val projectSettings: Seq[Setting[_]] = {
    import autoImport._
    Seq(
      sound.play(compile in Compile),
      sound.play(compile in Test),
      sound.play(test in Test, Sounds.Glass, Sounds.Basso)
    )
  }
    

  object autoImport {
    private def start(input: InputStream): Unit = AudioPlayer.player.start(new AudioStream(input))

    abstract class Sound { def play(): Unit }
    case class FileSound(path: String) extends Sound {
      def play(): Unit = start(new FileInputStream(path))
    }
    case class ResourceSound(res: String) extends Sound {
      def play(): Unit = start(getClass.getResourceAsStream(res))
    }
    object Sounds {
      case object None  extends Sound { def play(): Unit = () }
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
      def play[T](t: TaskKey[T]): Setting[Task[T]] = play(t, Sounds.None, Sounds.Basso)
      def play[T](t: TaskKey[T], success: String, failure: Sound): Setting[Task[T]] = play(t, FileSound(success), failure)
      def play[T](t: TaskKey[T], success: Sound,  failure: String): Setting[Task[T]] = play(t, success, FileSound(failure))
      def play[T](t: TaskKey[T], success: String, failure: String): Setting[Task[T]] = play(t, FileSound(success), FileSound(failure))
      def play[T](t: TaskKey[T], success: Sound,  failure: Sound): Setting[Task[T]] = {
        t := {
          t.result.value match {
            case Inc(inc: Incomplete) =>
              failure.play()
              throw inc
            case Value(v) =>
              success.play()
              v
          }
        }
      }
    }
  }
}
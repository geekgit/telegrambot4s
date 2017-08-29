import info.mukel.telegrambot4s.api.{ChatActions, Polling, TelegramBot}
import info.mukel.telegrambot4s.api.declarative.{Commands, Help}
import info.mukel.telegrambot4s.Implicits._

object HelpBot extends App with TelegramBot
  with Polling
  with Commands
  with Help
  with ChatActions {

  lazy val token = "211113992:AAGlXLvA9lwhltbdMBUYBasBmw1giegeqfc"

  onCommandWithHelp('hello)("Greets the user", "Awesome") {
    implicit msg =>
      replyMd("``` hello ```[[]]()([]) *pepe* _el cojo_".md)
      //reply("Hello " + msg.from.map(_.firstName).getOrElse("Mr.X"))
  }

  onCommandWithHelp('bye, 'adios)("By bye", "Awesome") {
    implicit msg =>
      reply("Bye bye" + msg.from.map(_.firstName).getOrElse("Mr.X"))
  }

  onCommandWithHelp('another, 'command)("Jajaja", "Misc") {
    implicit msg =>
  }

  onCommandWithHelp('pepe, 'el, 'cojo)("This is a very long descriptoon about what this command can do",
    "Another Category") {
    implicit msg =>
  }

  onCommandWithHelp('chicho, 'la, 'amenaza)("This is a very long descriptoon about what this command can do") {
    implicit msg =>
  }

  onCommand("2048") { implicit msg => }
  onCommand("2_048") { implicit msg => }
  onCommand('west, 'north) { implicit msg => }


  run()
}

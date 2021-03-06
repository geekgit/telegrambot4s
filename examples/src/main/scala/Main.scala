object Main extends  App {
  Console.println("Hello, bot!")
  if(args.length!=2) {
    println("Invalid argument format")
    println("Format: sbt run <bot> <token>")
    println("")
    println("Bot List:")
    println("AuthenticationBot")
    println("CallbacksBot")
    println("CommandsBot")
    println("CovfefeBot")
    println("DogeBot")
    println("EchoBot")
    println("ExceptionBot")
    println("LmgtfyBot")
    println("PerChatRequestsBot")
    println("PokerBot")
    println("PollingWithWebRoutes")
    println("QrCodesBot")
    println("RandomBot")
    println("RegexBot")
    println("SelfDestructBot")
    println("SpotifyBot")
    println("StatefulBot")
    println("TextToSpeechBot")
    println("WebhookBot")
  }
  else {
    def bot=args(0)
    def token=args(1)
    println(s"Bot: ${bot}")
    println(s"Token: ${token}")
    bot match {
      case "AuthenticationBot" => new AuthenticationBot(token).run()
      case "CallbacksBot" => new CallbacksBot(token).run()
      case "CovfefeBot" => new CovfefeBot(token).run()
      case "DogeBot" => new DogeBot(token).run()
      case "EchoBot" => new EchoBot(token).run()
      case "ExceptionBot" => new ExceptionBot(token).run()
      case "LmgtfyBot" => new LmgtfyBot(token).run()
      case "PerChatRequestsBot" => new PerChatRequestsBot(token).run()
      case "PollingWithWebRoutes" => new PollingWithWebRoutes(token).run()
      case "QrCodesBot" => new QrCodesBot(token).run()
      case "RandomBot" => new RandomBot(token).run()
      case "RegexBot" => new RegexBot(token).run()
      case "SelfDestructBot" => new SelfDestructBot(token).run()
      case "SpotifyBot" => new SpotifyBot(token).run()
      case "StatefulBot" => new StatefulBot(token).run()
      case "TextToSpeechBot" => new StatefulBot(token).run()
      case "WebhookBot" => new WebhookBot(token).run()
    }
  }
}
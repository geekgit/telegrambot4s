$TelegramBotName=(Get-ChildItem Env:TELEGRAM_BOT_NAME).value
$TelegramBotToken=(Get-ChildItem Env:TELEGRAM_BOT_TOKEN).value

sbt "project examples" "run $TelegramBotName $TelegramBotToken"
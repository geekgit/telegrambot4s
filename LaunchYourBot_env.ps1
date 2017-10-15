$TelegramBotName=(Get-ChildItem Env:TELEGRAM_BOT_NAME).value
$TelegramBotType=(Get-ChildItem Env:TELEGRAM_BOT_TYPE).value
$TelegramBotToken=(Get-ChildItem Env:TELEGRAM_BOT_TOKEN).value

sbt "project examples" "run $TelegramBotType $TelegramBotToken"
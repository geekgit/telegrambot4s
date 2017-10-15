#!/bin/bash
TelegramBotName="$TELEGRAM_BOT_NAME"
TelegramBotType="$TELEGRAM_BOT_TYPE"
TelegramBotToken="$TELEGRAM_BOT_TOKEN"

sbt "project examples" "run $TelegramBotType $TelegramBotToken"
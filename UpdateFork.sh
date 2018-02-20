#!/bin/bash
git remote add upstream https://github.com/mukel/telegrambot4s
git fetch upstream
git checkout master
git merge upstream/master
git push origin master
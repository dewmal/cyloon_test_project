#!/usr/bin/env bash
export MONGO_HOST=apps.egreenhive.com
GRADLE_HOME=/home/dewmal/applications/gradle-2.5
export GRADLE_HOME
PATH=$PATH:$GRADLE_HOME/bin
export PATH
gradle clean runOsgi

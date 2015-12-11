#!/usr/bin/env bash
GRADLE_HOME=/home/dewmal/applications/gradle-2.5
export GRADLE_HOME
PATH=$PATH:$GRADLE_HOME/bin
export PATH
gradle clean runOsgi

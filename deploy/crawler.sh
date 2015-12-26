#!/bin/sh

wget http://cyloon.com/webcrawler.jar
rm /cyloon/osgi/bundle/webcrawler.jar
mv webcrawler.jar /cyloon/osgi/bundle


JAVA="java"

# if JAVA_HOME exists, use it
if [ -x "$JAVA_HOME/bin/java" ]
then
  JAVA="$JAVA_HOME/bin/java"
else
  if [ -x "$JAVA_HOME/jre/bin/java" ]
  then
    JAVA="$JAVA_HOME/jre/bin/java"
  fi
fi

"$JAVA"  -jar /cyloon/osgi/org.apache.felix.main-4.4.0.jar  "$@"

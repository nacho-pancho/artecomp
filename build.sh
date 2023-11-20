#!/bin/bash
CLASSPATH="classes"
for i in `find lib/ -name '*.jar'`
do
CLASSPATH=$CLASSPATH:`pwd`/$i
done
export CLASSPATH
echo "CLASSPATH=$CLASSPATH"
javac -O -target 1.5 -Xlint:unchecked -d classes `find source -name '*.java'`


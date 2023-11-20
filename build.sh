#!/bin/bash
CLASSPATH="classes"
for i in `find lib/ -name '*.jar'`
do
CLASSPATH=$CLASSPATH:`pwd`/$i
done
export CLASSPATH
echo "CLASSPATH=$CLASSPATH"
javac -O  -Xlint:unchecked -Xlint:deprecation -d classes `find source -name '*.java'`


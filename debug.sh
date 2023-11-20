#!/bin/bash
CLASSPATH="classes"
for i in `find lib/ -name '*.jar'`
do
CLASSPATH=$CLASSPATH:`pwd`/$i
done
export CLASSPATH
echo "CLASSPATH=$CLASSPATH"
#javac  `find artecomp -name '*.java'`
PROG=$1
if [[ -z $PROG ]]
then
  PROG=artecomp.ui.ArteComp
fi
jdb -Xmx512M -Xms128M $PROG

#!/bin/bash
basedir=`dirname $0`
java -jar "$basedir/lib/${project.build.finalName}.jar"

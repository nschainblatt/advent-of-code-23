#!/bin/bash

build () {
  echo "Building project and saving to target directory"
  javac -d target src/com/aoc/*.java
}

run () {
  echo "Running build"
  javac -d target src/com/aoc/*.java
  cd target
  java com.aoc.Day2
}

if [[ $1 == "build" ]];
then
  build
elif [[ $1 == "" ]];
then
  run
fi

#!/bin/bash

build () {
  echo "Building project and saving to target directory"
  javac -d target src/com/aoc/*.java
}

run () {
  echo "Running build"
  ./run.sh build
  cd target
  java com.aoc.Day3Part1
}

test () {
  echo "Running tests"
  ./run.sh build
  cd target
  java com.aoc.Day3Part1Test
}

if [[ $1 == "build" ]];
then
  build

elif [[ $1 == "test" ]];
then
  test
elif [[ $1 == "" ]];
then
  run
fi

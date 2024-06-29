#!/bin/bash

build () {
  echo "Building project and saving to target directory"
  javac -d target $(find -type f -name "*.java")
}

run () {
  ./run.sh build
  echo "Running build"
  cd target
  java com/aoc/Main $@ # All argumants passed to this function seperated by spaces
}

test () {
  echo "Running tests"
  ./run.sh build
  cd target
  java com.aoc.tests.MainTest $@
}

clean () {
  echo "Cleaning target directory"
  rm -rf ./target/*
}

if [[ $1 == "build" ]];
then
  build
elif [[ $1 == "test" ]];
then
  test "${@:2}"
elif [[ $1 == "clean" ]]
then
  clean
elif [[ $1 == "up" ]];
then
  run "${@:2}" # Gives the run function all arguments from 2 and onwards seperatated by spaces (slicing)
fi

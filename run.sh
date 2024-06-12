#!/bin/bash

build () {
  echo "Building project and saving to target directory"
  javac -d target $(find -type f -name "*.java")
}

run () {
  ./run.sh build
  echo "Running build"
  cd target
  java com/aoc/day3/Day3Part1
}

test () {
  echo "Running tests"
  ./run.sh build
  cd target
  java com.aoc.day3.Day3Part1Test
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
  test
elif [[ $1 == "clean" ]]
then
  clean
elif [[ $1 == "" ]];
then
  run
fi

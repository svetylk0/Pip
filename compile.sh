#!/bin/bash

if [ ! -d build ]; then
  mkdir build
fi

fsc -cp .:lib/twitter4j-core-actual.jar -deprecation -d build src/pip/**/*.scala

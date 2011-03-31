#!/bin/bash

if [ ! -d build ]; then
  mkdir build
fi

fsc -cp .:lib/twitter4j-core-2.2.2-SNAPSHOT.jar -deprecation -d build src/pip/**/*.scala

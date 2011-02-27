#!/bin/bash

if [ ! -d build ]; then
  mkdir build
fi

fsc -cp .:lib/twitter4j-core-2.2.1-SNAPSHOT.jar -d build src/pip/**/*.scala

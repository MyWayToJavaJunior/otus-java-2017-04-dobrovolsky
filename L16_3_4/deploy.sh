#!/usr/bin/env bash

mvn clean package
java -jar MessageServer/target/MessageServer.jar -count 2

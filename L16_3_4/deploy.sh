#!/usr/bin/env bash

mvn clean
mvn package
cp -r Frontend/templates MessageServer/target/
java -jar MessageServer/target/MessageServer.jar

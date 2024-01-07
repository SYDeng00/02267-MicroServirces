#!/bin/bash

mvn package
java -jar .\target\quarkus-app\quarkus-run.jar
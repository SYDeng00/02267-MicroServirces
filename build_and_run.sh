#!/bin/bash
set -e

pushd AS1/Server
mvn package
# Create a new docker image if necessary.
docker-compose build
# Restarts the container with the new image if necessary
docker-compose up -d
# The server stays running.
# To terminate the server run docker-compose down in the
# code-with-quarkus direcgtory
# clean up images
docker image prune -f 
popd

# Give the Web server a chance to finish start up
sleep 2 

pushd AS1/Client
mvn test
popd

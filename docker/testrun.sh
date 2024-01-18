#!/bin/bash

pushd message-utils
chmod +x build.sh 
./build.sh
popd 

docker image prune -f 

docker-compose up  -d --build rabbitMq
sleep 10s
docker-compose up  -d --build tokens
sleep 3s
docker-compose up  -d --build account
sleep 3s
docker-compose up -d --build payment
sleep 3s
docker-compose up  -d --build facade
sleep 3s

pushd client
mvn test
popd

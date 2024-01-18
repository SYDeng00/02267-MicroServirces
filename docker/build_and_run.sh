#!/bin/bash
set -e

# Build and install the libraries
# abstracting away from using the
# RabbitMq message queue
pushd message-utils
chmod +x build.sh 
./build.sh
popd 


#dtu-pay-facade
pushd dtu_facade
mvn package -Dquarkus.package.type=uber-jar
popd

#Account
pushd account_microservice
mvn clean package
popd

#Token
pushd TokenManagement
mvn clean package
popd

#Payment
pushd payment_microservice
mvn clean package
popd


# clean up images
docker image prune -f 

#Docker Compose
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








# Give the Web server a chance to finish start up

pushd client
mvn test
popd


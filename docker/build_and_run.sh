# /*
# Author: Siyuan Deng s232275  
# */

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
mvn clean package 
popd

#Account
pushd account_microservice
mvn clean package
popd

#Token
pushd token_microservice
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
sleep 10
docker-compose up  -d --build tokens
sleep 3
docker-compose up  -d --build account
sleep 3
docker-compose up -d --build payment
sleep 3
docker-compose up  -d --build facade
sleep 3








# Give the Web server a chance to finish start up

pushd client
mvn test
popd


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


docker image prune -f 

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

pushd client
mvn test
popd

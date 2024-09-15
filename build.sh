#!/bin/bash
export JAVA_HOME=$(jenv javahome)
echo $JAVA_HOME
cd ./job-models
./mvnw clean install
cd ../messaging
./mvnw clean install -DskipTests
docker build -t angshu/js-messaging .
cd ../job-acceptor-scanner
./mvnw clean install -DskipTests
docker build -t angshu/job-acceptor .


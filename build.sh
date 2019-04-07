#!/usr/bin/env bash
cd customer-service
pwd
./gradlew clean build
cd ../product-service
pwd
./gradlew clean build
cd ../price-service
pwd
./gradlew clean build
cd ../order-service
pwd
./gradlew clean build
cd ../api-gateway
pwd
./gradlew clean build
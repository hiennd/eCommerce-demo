#!/usr/bin/env bash
java -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -jar -Dspring.profiles.active=default ./customer-service/build/libs/customer-service-0.0.1-SNAPSHOT.jar&
java -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -jar -Dspring.profiles.active=default ./product-service/build/libs/product-service-0.0.1-SNAPSHOT.jar&
java -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -jar -Dspring.profiles.active=default ./price-service/build/libs/price-service-0.0.1-SNAPSHOT.jar&
java -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -jar -Dspring.profiles.active=default ./order-service/build/libs/order-service-0.0.1-SNAPSHOT.jar&
java -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -jar -Dspring.profiles.active=default ./api-gateway/build/libs/api-gateway-0.0.1-SNAPSHOT.jar
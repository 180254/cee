#!/bin/bash

cd "$(dirname -- "$0")"

(cd app/ && ./mvnw clean package -DskipTests=true)
cp app/target/login-app-0.0.1.jar ./

vagrant ssh ignite1 -c " pkill -f login-app"
vagrant ssh ignite1 -c " cp /vagrant/login-app-0.0.1.jar /home/vagrant"
vagrant ssh ignite1 -c " nohup java -jar -Dserver.port=9000 -Xms512m -Xmx512m /home/vagrant/login-app-0.0.1.jar & sleep 1"
vagrant ssh ignite1 -c " nohup java -jar -Dserver.port=9001 -Xms512m -Xmx512m /home/vagrant/login-app-0.0.1.jar & sleep 1"

rm login-app-0.0.1.jar

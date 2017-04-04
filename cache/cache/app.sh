#!/bin/bash

cd "$(dirname -- "$0")"

(cd app/ && ./mvnw clean package -DskipTests=true)
cp app/target/login-app-0.0.1.jar ./

vagrant ssh ignite1 -c " pkill -f login-app"
vagrant ssh ignite1 -c " cp /vagrant/login-app-0.0.1.jar /home/vagrant"
vagrant ssh ignite1 -c " nohup java -jar -Dserver.port=9000 /home/vagrant/login-app-0.0.1.jar > nohup0.out 2>&1 & sleep 1"
vagrant ssh ignite1 -c " nohup java -jar -Dserver.port=9001 /home/vagrant/login-app-0.0.1.jar > nohup1.out 2>&1 & sleep 1"

vagrant ssh ignite2 -c " pkill -f login-app"
vagrant ssh ignite2 -c " pkill -f ignite"
vagrant ssh ignite2 -c " cp /vagrant/login-app-0.0.1.jar /home/vagrant"
vagrant ssh ignite2 -c " nohup java -jar -Dserver.port=9000 /home/vagrant/login-app-0.0.1.jar  > nohup0.out 2>&1 & sleep 1"
vagrant ssh ignite2 -c " nohup /usr/local/ignite/bin/ignite.sh /home/vagrant/config-ignite.xml > nohup1.out 2>&1 & sleep 1"

rm login-app-0.0.1.jar

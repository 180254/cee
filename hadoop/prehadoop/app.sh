#!/bin/bash

(cd  ../hadoop/ && ./mvnw clean package)
cp ../hadoop/target/password-entropy-0.0.1-jar-with-dependencies.jar ./

vagrant ssh hadoop-master -c " pkill -f password-entropy"
vagrant ssh hadoop-master -c " cp /vagrant/password-entropy-0.0.1-jar-with-dependencies.jar /home/vagrant"
vagrant ssh hadoop-master -c " nohup /usr/local/hadoop/bin/hadoop jar /home/vagrant/password-entropy-0.0.1-jar-with-dependencies.jar & sleep 1"
rm password-entropy-0.0.1-jar-with-dependencies.jar

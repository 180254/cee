#!/bin/bash

vagrant up
vagrant ssh hadoop-master -c "/usr/local/hadoop/bin/hdfs namenode -format"
vagrant ssh hadoop-master -c "/usr/local/hadoop/bin/hdfs dfs -mkdir /user"
vagrant ssh hadoop-master -c "/usr/local/hadoop/bin/hdfs dfs -mkdir /user/vagrant"
vagrant ssh hadoop-master -c "/usr/local/hadoop/sbin/start-dfs.sh"
vagrant ssh hadoop-master -c "/usr/local/hadoop/sbin/start-yarn.sh"


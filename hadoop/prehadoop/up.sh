#!/bin/bash

if   [ "$1" == "0" ]; then vagrant up hadoop-master
elif [ "$1" == "1" ]; then vagrant up hadoop-master hadoop-slave1
elif [ "$1" == "2" ]; then vagrant up hadoop-master hadoop-slave1 hadoop-slave2
else
    echo "usage: $0 number_of_slaves"
    exit 100
fi

if [ $? -eq 0 ]; then
    vagrant ssh hadoop-master -c "/usr/local/hadoop/bin/hdfs namenode -format"
    vagrant ssh hadoop-master -c "/usr/local/hadoop/sbin/start-dfs.sh"
    vagrant ssh hadoop-master -c "/usr/local/hadoop/sbin/start-yarn.sh"
    vagrant ssh hadoop-master -c "/usr/local/hadoop/bin/hdfs dfs -mkdir -p /user/vagrant/input"
    vagrant ssh hadoop-master -c "/usr/local/hadoop/bin/hdfs dfs -mkdir -p /user/vagrant/output"
    vagrant ssh hadoop-master -c "/usr/bin/jps"
else
    echo "$0 failed, code: $?"
    exit $?
fi


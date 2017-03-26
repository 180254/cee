#!/bin/bash

vagrant up

if [ $? -eq 0 ]; then
    vagrant ssh hadoop-master -c "/usr/local/hadoop/bin/hdfs namenode -format"
    vagrant ssh hadoop-master -c "/usr/local/hadoop/sbin/start-dfs.sh"
    vagrant ssh hadoop-master -c "/usr/local/hadoop/sbin/start-yarn.sh"

    vagrant ssh hadoop-master -c "/usr/local/hadoop/bin/hdfs dfs -mkdir /user"
    vagrant ssh hadoop-master -c "/usr/local/hadoop/bin/hdfs dfs -mkdir /user/vagrant"

    vagrant ssh hadoop-master -c "/usr/bin/jps"
    vagrant ssh hadoop-slave1 -c "/usr/bin/jps"

else
    echo "up.sh failed, code: $?"
fi


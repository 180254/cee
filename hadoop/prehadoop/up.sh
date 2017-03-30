#!/bin/bash

cd "$(dirname -- "$0")"

main()
{
    if [ ! -f files/resources/hadoop-2.6.4.tar.gz ]; then
        wget "http://ftp.piotrkosoft.net/pub/mirrors/ftp.apache.org/hadoop/common/hadoop-2.6.4/hadoop-2.6.4.tar.gz" -P files/resources/
    fi

    if [ ! -f files/archives/influxdb_1.2.1-1_amd64.deb ]; then
        wget "https://repos.influxdata.com/ubuntu/pool/stable/i/influxdb/influxdb_1.2.1-1_amd64.deb" -P files/archives/
    fi

    # ----------------------------------------

    vagrant up $1

    # ----------------------------------------

    if [ $? -eq 0 ]; then
        vagrant ssh hadoop-master -c " /usr/local/hadoop/bin/hdfs namenode -format"
        vagrant ssh hadoop-master -c " /usr/local/hadoop/sbin/start-dfs.sh"
        vagrant ssh hadoop-master -c " /usr/local/hadoop/sbin/start-yarn.sh"
        vagrant ssh hadoop-master -c " /usr/local/hadoop/bin/hdfs dfs -mkdir -p /user/vagrant/input"
       #vagrant ssh hadoop-master -c " /usr/local/hadoop/bin/hdfs dfs -mkdir -p /user/vagrant/output"
        vagrant ssh hadoop-master -c " dsh -M -g cluster 'jps'"
    else
        echo "$0 failed, code: $?"
        exit $?
    fi
}

# ----------------------------------------

if   [ "$1" == "0" ]; then
    main "hadoop-master"
elif [ "$1" == "1" ]; then
    main "hadoop-master hadoop-slave1"
elif [ "$1" == "2" ]; then
    main "hadoop-master hadoop-slave1 hadoop-slave2"
else
    echo "usage: $0 number_of_slaves_<0,2>"
    exit 100
fi

#!/bin/bash

echo "------- shell gethadoop -------"

if [[ $(hostname) == "hadoop-master" ]]; then
    rm -rf /usr/local/hadoop

    if [ -f /home/vagrant/resources/hadoop-2.6.4.tar.gz ]; then
        mv /home/vagrant/resources/hadoop-2.6.4.tar.gz /usr/local/
    else
      wget "http://ftp.piotrkosoft.net/pub/mirrors/ftp.apache.org/hadoop/common/hadoop-2.6.4/hadoop-2.6.4.tar.gz" -P /usr/local
    fi

    cd /usr/local/
    tar -xzf hadoop-2.6.4.tar.gz
    mv hadoop-2.6.4 hadoop
    chown vagrant:vagrant -R /usr/local/hadoop

else
    rm -rf /usr/local/hadoop
    scp -r vagrant@hadoop-master:/usr/local/hadoop /usr/local/hadoop
    chown vagrant:vagrant -R /usr/local/hadoop

fi

HADOOP_HOME=/usr/local/hadoop
JAVA_HOME=$(readlink -f /usr/bin/java | sed "s:bin/java::")
echo "export JAVA_HOME=${JAVA_HOME}" >> ${HADOOP_HOME}/etc/hadoop/hadoop-env.sh

mv /home/vagrant/hadoop/* /usr/local/hadoop/etc/hadoop/

mkdir -p /home/vagrant/tmp
mkdir -p /home/vagrant/hadoop/yarn_data/hdfs/namenode
mkdir -p /home/vagrant/hadoop/yarn_data/hdfs/datanode

chown vagrant:vagrant /home/vagrant/tmp
chown vagrant:vagrant /home/vagrant/hadoop/yarn_data/hdfs/namenode
chown vagrant:vagrant /home/vagrant/hadoop/yarn_data/hdfs/datanode

#!/bin/bash

echo "------- SHELL GETHADOOP -------"

if [[ $(hostname) = "hadoop-master" ]]; then

    echo "hadoop-master mode"

    rm -rf /usr/local/hadoop

    hadoopTarGz=/home/vagrant/files/resources/hadoop-2.6.4.tar.gz

    if [ -f $hadoopTarGz ]; then
        mv $hadoopTarGz /usr/local/
    else
        wget "http://ftp.piotrkosoft.net/pub/mirrors/ftp.apache.org/hadoop/common/hadoop-2.6.4/hadoop-2.6.4.tar.gz" -P /usr/local
    fi

    cd /usr/local/
    tar -xzf hadoop-2.6.4.tar.gz
    mv hadoop-2.6.4 hadoop
    chown vagrant:vagrant -R /usr/local/hadoop

    mv /home/vagrant/files/etc-hadoop/* /usr/local/hadoop/etc/hadoop/

else
    echo "hadoop-slave mode"

    scp -r vagrant@hadoop-master:/usr/local/hadoop /home/vagrant/hadoop
    rm -rf /usr/local/hadoop
    mv /home/vagrant/hadoop /usr/local/
    chown vagrant:vagrant -R /usr/local/hadoop
fi



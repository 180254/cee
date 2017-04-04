#!/bin/bash

echo "------- shell getignite -------"

apt-get install -y unzip

rm -rf /usr/local/ignite

if [ -f /home/vagrant/resources/apache-ignite-fabric-1.9.0-bin.zip ]; then
    mv /home/vagrant/resources/apache-ignite-fabric-1.9.0-bin.zip /usr/local/
else
    wget "http://ftp.piotrkosoft.net/pub/mirrors/ftp.apache.org/ignite/1.9.0/apache-ignite-fabric-1.9.0-bin.zip" -P /usr/local
fi

cd /usr/local/
unzip -q apache-ignite-fabric-1.9.0-bin.zip -d .
mv apache-ignite-fabric-1.9.0-bin ignite
chown vagrant:vagrant -R /usr/local/ignite

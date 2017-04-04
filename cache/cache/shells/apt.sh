#!/bin/bash

echo "------- shell apt -------"

mv /home/vagrant/archives/* /var/cache/apt/archives/ 2> /dev/null
apt-get install -y openjdk-8-jdk dos2unix

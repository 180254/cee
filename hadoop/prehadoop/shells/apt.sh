#!/bin/bash

echo "------- shell apt -------"

mv /home/vagrant/archives/* /var/cache/apt/archives/ 2> /dev/null

apt-get install -y rsync ssh openjdk-8-jdk dsh htop dos2unix

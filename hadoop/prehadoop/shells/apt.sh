#!/bin/bash

echo "------- shell apt -------"

mv /home/vagrant/archives/*  /var/cache/apt/archives/

apt-get install -y rsync ssh openjdk-8-jre dsh htop


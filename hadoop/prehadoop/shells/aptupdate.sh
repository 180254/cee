#!/bin/bash

echo "------- shell aptupdate -------"

mv /home/vagrant/lists/* /var/lib/apt/lists/ 2> /dev/null

add-apt-repository ppa:openjdk-r/ppa

apt-get update

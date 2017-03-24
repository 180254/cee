#!/bin/bash

echo "------- shell aptupdate -------"

mv /home/vagrant/lists/*     /var/lib/apt/lists/

add-apt-repository ppa:openjdk-r/ppa

apt-get update


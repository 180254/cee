#!/bin/bash

echo "------- shell generic -------"

cat /home/vagrant/system/bashrc  >> /home/vagrant/.bashrc
mv  /home/vagrant/system/hosts      /etc/hosts
mv  /home/vagrant/system/config     /home/vagrant/.ssh/config
mv  /home/vagrant/system/cluster    /etc/dsh/group/cluster
mv  /home/vagrant/system/slaves     /etc/dsh/group/slaves


#!/bin/bash

echo "-------- hdfs_format --------"

sudo rm -rf /home/vagrant/tmp
hdfs namenode -format

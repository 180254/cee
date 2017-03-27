#!/bin/bash

echo "-------- hdfs_format --------"

sudo rm -rf /tmp/hadoop-vagrant
hdfs namenode -format
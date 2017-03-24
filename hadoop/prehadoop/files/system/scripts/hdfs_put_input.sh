#!/bin/bash

echo "-------- hdfs_put_input --------"

hdfs dfs -rm -r /user/vagrant/output
hdfs dfs -put /usr/local/hadoop/etc/hadoop/ input
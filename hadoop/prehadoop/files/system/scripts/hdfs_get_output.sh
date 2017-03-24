#!/bin/bash

echo "-------- hdfs_get_output --------"

hdfs dfs -get output /home/vagrant/output
cat /home/vagrant/output/* | wc -l
#!/bin/bash

echo "-------- demo --------"

sh hadoop_start.sh
sh hdfs_mkdir_input.sh
sh hdfs_put_input.sh
sh grep_run.sh
sh hdfs_get_output.sh
cat /home/vagrant/output/*

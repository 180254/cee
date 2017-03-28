#!/bin/bash

echo "-------- demopi --------"

sh hadoop_start.sh; 
sh hdfs_mkdir_input.sh; 
sh pi_hadoop.sh

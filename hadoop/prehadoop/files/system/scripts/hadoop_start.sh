#!/bin/bash

echo "-------- hadoop_start --------"

sh jps.sh
sh hdfs_format.sh
start-dfs.sh
start-yarn.sh
sh jps.sh
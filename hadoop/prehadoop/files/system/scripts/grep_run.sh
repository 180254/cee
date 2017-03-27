#!/bin/bash

echo "-------- grep_run --------"

hadoop jar /usr/local/hadoop/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.6.4.jar grep input/hadoop output 'dfs[a-z]+'
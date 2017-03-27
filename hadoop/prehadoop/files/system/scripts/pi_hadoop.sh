#!/bin/bash

echo "-------- pi_hadoop --------"

num_maps = 100
num_points = 1000
hadoop jar /usr/local/hadoop/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.6.4.jar pi $num_maps $num_points

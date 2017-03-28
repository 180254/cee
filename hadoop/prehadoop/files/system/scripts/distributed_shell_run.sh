#!/bin/bash

echo "-------- distributed_shell_run --------"

hadoop org.apache.hadoop.yarn.applications.distributedshell.Client -jar /usr/local/hadoop/share/hadoop/yarn/hadoop-yarn-applications-distributedshell-2.6.4.jar -shell_command cal -num_containers 3

#!/bin/bash

echo "-------- clear_datanode_nodes --------"

dsh -M -g cluster "sudo rm -rf /tmp/hadoop-vagrant"
sh jps_node.sh
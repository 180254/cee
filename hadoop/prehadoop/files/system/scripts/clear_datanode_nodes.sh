#!/bin/bash

echo "-------- clear_datanode_nodes --------"

dsh -M -g cluster "sudo rm -rf /home/vagrant/tmp"
sh jps_node.sh

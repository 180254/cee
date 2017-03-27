#!/bin/bash

echo "------- shell monitoring -------"

cd /home/vagrant/
tar xf monitoring.tar
cd monitoring
sh install.sh

rm -rf /home/vagrant/monitoring*


#!/bin/bash

echo "------- shell monitoring -------"

# influxdb may be successfully downloaded via wget,
# but there is some problem with standard apt downloading.
if [ ! -f /var/cache/apt/archives/influxdb_1.2.1-1_amd64.deb ]; then
    wget "https://repos.influxdata.com/ubuntu/pool/stable/i/influxdb/influxdb_1.2.1-1_amd64.deb" -P /var/cache/apt/archives/
fi

mv /home/vagrant/resources/monitoring.tar /home/vagrant/monitoring.tar

cd /home/vagrant/
tar xf monitoring.tar
cd monitoring
sh install.sh

rm -rf /home/vagrant/monitoring*

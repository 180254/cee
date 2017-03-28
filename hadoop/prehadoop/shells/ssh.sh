#!/bin/bash

echo "------- shell ssh -------"

mv /home/vagrant/system/id_rsa /home/vagrant/.ssh/id_rsa
mv /home/vagrant/system/id_rsa.pub /home/vagrant/.ssh/id_rsa.pub

chown vagrant:vagrant /home/vagrant/.ssh/id_rsa
chown vagrant:vagrant /home/vagrant/.ssh/id_rsa.pub

cat /home/vagrant/.ssh/id_rsa.pub >> /home/vagrant/.ssh/authorized_keys

cp -r /home/vagrant/.ssh /root/

chmod 600 /home/vagrant/.ssh/id_rsa
chmod 644 /home/vagrant/.ssh/id_rsa.pub
chmod 600 /root/.ssh/id_rsa
chmod 644 /root/.ssh/id_rsa.pub

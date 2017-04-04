#!/bin/bash

echo "------- shell getnginx -------"

apt-get install -y nginx
mv /home/vagrant/nginx/default /etc/nginx/sites-available/default
service nginx restart

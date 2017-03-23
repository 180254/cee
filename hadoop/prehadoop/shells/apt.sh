#!/bin/bash

echo "------- SHELL APT -------"

export DEBIAN_FRONTEND=noninteractive

apt-get update
apt-get install -y rsync ssh


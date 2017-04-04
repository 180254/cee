#!/bin/bash

echo "------- shell generic -------"

find /home/vagrant -type f -print0 | xargs -0 dos2unix 2> /dev/null

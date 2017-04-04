#!/bin/bash

cd "$(dirname -- "$0")"

if [ ! -f files/resources/apache-ignite-fabric-1.9.0-bin.zip ]; then
    wget "http://ftp.piotrkosoft.net/pub/mirrors/ftp.apache.org/ignite/1.9.0/apache-ignite-fabric-1.9.0-bin.zip" -P files/resources/
fi

vagrant up

if [ $? -eq 0 ]; then
    echo "$0 ok, code: $?, you may start app.sh"
else
    echo "$0 failed, code: $?"
    exit $?
fi

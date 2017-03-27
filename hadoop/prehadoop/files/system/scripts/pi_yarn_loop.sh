#!/bin/bash

echo "-------- pi_yarn_loop --------"


x=1
while [ $x -le 5 ]
do
  sh pi_yarn.sh
done

echo "Time elapsed log:"
cat time.log
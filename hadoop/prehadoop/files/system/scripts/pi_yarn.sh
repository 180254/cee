#!/bin/bash

echo "-------- pi_yarn --------"

TIME_LOG="time.log"
echo "pi_yarn" >> $TIME_LOG
STARTTIME=$(date +%s)
echo "$STARTTIME" >> $TIME_LOG
hadoop jar /usr/local/hadoop/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.6.4.jar pi 100 2000
ENDTIME=$(date +%s)
echo "$ENDTIME" >> $TIME_LOG

echo "It takes $(($ENDTIME - $STARTTIME)) seconds to complete this task..." >> $TIME_LOG
echo "" >> $TIME_LOG
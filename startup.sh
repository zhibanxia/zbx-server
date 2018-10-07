#!/bin/bash

PROJECT_NAME=zbx-server
LOG_PATH=$HOME/logs/$PROJECT_NAME
JAR_FILE=./build/libs/$PROJECT_NAME-0.1.jar

function start() {
        JAVA_OPTS="$JAVA_OPTS -server
               -Xmx4g
               -Xms256m
               -Xmn256m
               -XX:MetaspaceSize=256m
               -XX:MaxMetaspaceSize=1g
               -Xss256k
               -XX:+ExplicitGCInvokesConcurrent
               -XX:+UseConcMarkSweepGC
               -XX:+UseCMSInitiatingOccupancyOnly
               -XX:CMSInitiatingOccupancyFraction=70
               -XX:-UseBiasedLocking
               -XX:AutoBoxCacheMax=20000
               -XX:MaxTenuringThreshold=6
               -Xloggc:$LOG_PATH/gc_$PROJECT_NAME.log
               -XX:+PrintGCApplicationStoppedTime
               -XX:+PrintGCDateStamps
               -XX:+PrintGCDetails
               -XX:+PrintCodeCache
               -XX:+UseGCLogFileRotation
               -XX:NumberOfGCLogFiles=2
               -XX:+PrintGCDateStamps
               -XX:+PrintGCDetails
               -XX:+PrintCodeCache
               -XX:+UseGCLogFileRotation
               -XX:NumberOfGCLogFiles=2
               -XX:GCLogFileSize=10m
               -XX:+HeapDumpOnOutOfMemoryError
               -XX:HeapDumpPath=$LOG_PATH/"

          nohup java $JAVA_OPTS -jar $JAR_FILE 2>$LOG_PATH/console_error.log 1>/dev/null &
}

function stop() {
        process=`ps aux | grep java | grep $JAR_FILE | grep -v "grep"|grep -v "restart"|grep -v "stop"| wc -l`
        if [[ 0 != $process ]];then
                ps -ef | grep $JAR_FILE | grep -v "grep"|grep -v "stop"|grep -v "restart"| awk '{print $2}' | xargs kill -15
        fi
        sleep 3
        if [[ 0 != $process ]];then
                ps -ef | grep $JAR_FILE | grep -v "grep"|grep -v "stop"|grep -v "restart"| awk '{print $2}' | xargs kill -9
        fi
}

function restart() {
        stop
        sleep 3
        start
}

function status() {
        ps aux | grep java | grep $JAR_FILE | grep -v grep| awk '{print $2}'
}

function help() {
    echo "$0 |start|stop|restart|status|"
}


if [ "$1" == "" ]; then
    help
elif [ "$1" == "stop" ];then
    stop
elif [ "$1" == "start" ];then
    start
elif [ "$1" == "restart" ];then
    restart
elif [ "$1" == "status" ];then
    status
else
    help
fi
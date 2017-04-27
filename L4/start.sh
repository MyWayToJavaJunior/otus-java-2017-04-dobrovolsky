#!/usr/bin/env bash


REMOTE_DEBUG="-agentlib:jdwp=transport=dt_socket,address=14025,server=y,suspend=n"
rm -rf jvm_*.out
rm -rf ./logs/*
MEMORY="-Xms512m -Xmx512m -XX:MaxMetaspaceSize=256m"
GC_LOG=" -verbose:gc -Xloggc:./logs/gc_pid_%p.log -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=100M"
rm -rf ./dumps/*
REMOTE_DEBUG="-agentlib:jdwp=transport=dt_socket,address=14025,server=y,suspend=n"
DUMP="-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./dumps/"

mvn clean package

if [ ! -d "./logs" ]; then
    mkdir "./logs"
fi

if [ ! -d "./dumps" ]; then
    mkdir "./dumps"
fi

for f in "-XX:+UseSerialGC" "-XX:+UseParallelGC" "-XX:+UseParallelOldGC" "-XX:+UseParNewGC -XX:+UseConcMarkSweepGC" "-XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 -XX:+ScavengeBeforeFullGC -XX:+CMSScavengeBeforeRemark" "-XX:+UseG1GC"
do
    GC=$f
    DATE=$(date +"%Y%m%d_%H:%M:%S")
    JVM_OUT="jvm_$DATE.out"
    echo "Current GC mode: "$GC
    java $REMOTE_DEBUG $MEMORY $GC $GC_LOG $JMX $DUMP -XX:OnOutOfMemoryError="kill -3 %p" -jar target/2017-04-L4.jar > $JVM_OUT
done



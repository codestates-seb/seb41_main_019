#!/bin/bash

BUILD_JAR=$(ls /home/ubuntu/action/build/libs/server-0.0.1-SNAPSHOT.jar)
JAR_NAME=$(basename $BUILD_JAR)

echo "> 현재 시간: $(date)" >> /home/ubuntu/action/deploy.log

echo "> build 파일명: $JAR_NAME" >> /home/ubuntu/action/deploy.log

echo "> build 파일 복사" >> /home/ubuntu/action/deploy.log
DEPLOY_PATH=/home/ubuntu/action/
cp $BUILD_JAR $DEPLOY_PATH

echo "> 현재 실행중인 애플리케이션 pid 확인" >> /home/ubuntu/action/deploy.log
CURRENT_PID=$(pgrep -f $JAR_NAME)

echo "> 현재 실행중인 애플리케이션 pid 확인" >> /home/ubuntu/action/deploy.log
CURRENT_PID=$(pgrep -f redis-server-2.8.19)

if [ -z $CURRENT_PIDD ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> /home/ubuntu/action/deploy.log
else
  echo "> sudo service redis stop" >> /home/ubuntu/action/deploy.log
  sudo service redis stop
  sleep 5
fi

if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> /home/ubuntu/action/deploy.log
else
  echo "> kill -9 $CURRENT_PID" >> /home/ubuntu/action/deploy.log
  sudo kill -9 $CURRENT_PID
  sleep 5
fi

echo "> DEPLOY_JAR 배포"    >> /home/ubuntu/action/deploy.log
cd /home/ubuntu/action/build/libs/
sudo nohup java -jar server-0.0.1-SNAPSHOT.jar --spring.profiles.active=server >> /home/ubuntu/deploy.log 2>/home/ubuntu/action/deploy_err.log &

echo "> redis 서버 실행"    >> /home/ubuntu/action/deploy.log
sudo service redis start

echo "> redis 서버 password설정"    >> /home/ubuntu/action/deploy.log
redis-cli

echo "> redis 서버 password설정"    >> /home/ubuntu/action/deploy.log
config set requirepass ${{ secrets.REDIS_PASSWORD }}

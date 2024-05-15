FROM amazoncorretto:17
MAINTAINER xiaowoniu

ADD ./target/snail-job-example.jar snail-job-example.jar

#对外暴漏的端口号
EXPOSE 8018

WORKDIR /

#开机启动
ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS /snail-job-example.jar $PARAMS"]


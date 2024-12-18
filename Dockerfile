FROM amazoncorretto:21
MAINTAINER xiaowoniu

ADD ./target/example.jar example.jar

#对外暴漏的端口号
EXPOSE 8018

WORKDIR /

#开机启动
ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS /example.jar $PARAMS"]


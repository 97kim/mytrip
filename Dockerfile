FROM openjdk:8-jdk-alpine
COPY build/libs/*.jar app.jar
RUN ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime && echo Asia/Seoul > /etc/timezone
ENTRYPOINT ["java","-jar","/app.jar"]
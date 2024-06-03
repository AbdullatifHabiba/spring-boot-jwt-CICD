FROM openjdk:16-jdk-alpine

RUN mkdir -p /app
WORKDIR /app
COPY /target/*.jar /app/jwt-demo.jar
EXPOSE 8082

ENV JAVA_ARGS=""
CMD java $JAVA_ARGS -jar jwt-demo.jar

FROM openjdk:16-jdk-alpine

RUN mkdir -p /app
WORKDIR /app
COPY ./secure-me-1.0.jar ./jwt-demo.jar

EXPOSE 8082

ENV JAVA_ARGS=""
CMD java $JAVA_ARGS -jar jwt-demo.jar

FROM openjdk:17
EXPOSE 8081
ARG JAR_FILE=target/messaging-*.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
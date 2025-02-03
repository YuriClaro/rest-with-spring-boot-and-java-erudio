FROM openjdk:21-jdk

EXPOSE 8080

ARG JAR_FILE=target/rest-with-spring-boot-and-java-erudio-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

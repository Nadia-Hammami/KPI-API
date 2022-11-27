FROM maven:3.6.3 AS maven
LABEL MAINTAINER="bacemhmoudi92@gmail.com"

WORKDIR /usr/src/app
COPY . /usr/src/app
# Compile and package the application to an executable JAR
RUN mvn clean package

# For Java 11,
FROM adoptopenjdk/openjdk11:alpine-jre

ARG JAR_FILE=spring-boot-api-tutorial.jar

WORKDIR /opt/app

# Copy the spring-boot-api-tutorial.jar from the maven stage to the /opt/app directory of the current stage.
COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app/
COPY --from=maven /usr/src/app/target/word /opt/app/

ENTRYPOINT ["java","-jar","spring-boot-api-tutorial.jar"]
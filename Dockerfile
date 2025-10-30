# AS <NAME> to name this stage as maven
FROM maven:3.9.4-eclipse-temurin-17 AS maven
LABEL MAINTAINER="ikevintec@gmail.com"

WORKDIR /usr/src/app
COPY . /usr/src/app
# Compile and package the application to an executable JAR
RUN mvn package 

# For Java.
FROM openjdk:17

ARG JAR_FILE=nisum-0.0.1-SNAPSHOT.jar

WORKDIR /app

# Copy.
COPY --from=maven /usr/src/app/target/${JAR_FILE} /app

# Exposing port 8080
EXPOSE 8080

CMD ["java", "-jar", "nisum-0.0.1-SNAPSHOT.jar"]

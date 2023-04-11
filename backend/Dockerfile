# Start with Maven base image to build the application
FROM maven:3.8.4-jdk-8 AS builder

# Set working directory
WORKDIR /app

# Copy the source code and pom.xml file to the container
COPY src /app/src
COPY pom.xml /app/pom.xml

# Compile the application and package it as a JAR file
RUN mvn install

# Start with Java 8 base image for the final container
FROM openjdk:8-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the executable JAR file to the final container from the builder image
COPY --from=builder /app/target/backend-0.0.1-SNAPSHOT.jar /app/backend.jar

# Copy the movie documents into the final container
COPY src/main/python/documents/ /app/documents/movieJsons/
COPY src/main/resources/static/database/ /app/documents/database
COPY src/main/resources/static/es-settings.json /app/resources/es-settings.json
COPY src/main/resources/static/mappings/movie.json /app/resources/movie.json

EXPOSE 9187

# Start application
CMD ["java", "-jar", "backend.jar"]

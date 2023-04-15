# Start with Maven base image to build the application
FROM maven:3.8.4-jdk-8 AS builder

# Set working directory
WORKDIR /app

# Copy the source code and pom.xml file to the container
COPY pom.xml /app/pom.xml

RUN mvn dependency:go-offline

COPY src /app/src
COPY ./target/backend-0.0.1-SNAPSHOT.jar /app/app.jar




# Copy the movie documents into the final container
COPY src/main/python/documents/ /app/documents/movieJsons/
COPY src/main/resources/static/database/ /app/documents/database
COPY src/main/resources/static/es-settings.json /app/resources/es-settings.json
COPY src/main/resources/static/mappings/movie.json /app/resources/movie.json

EXPOSE 9187

# Start application
CMD ["java", "-jar", "app.jar"]

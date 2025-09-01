# Use an OpenJDK image as the base
FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the Spring Boot jar file into the container
COPY target/*.jar app.jar

# Expose port 8080 for the application
EXPOSE 8080

# Create a data directory for application data
RUN mkdir data

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
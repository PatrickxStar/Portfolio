# Use the official OpenJDK image for Java 17
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the target directory to the container
COPY target/miniproject-0.0.1-SNAPSHOT.jar /app/miniproject.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the JAR file
CMD ["java", "-jar", "miniproject.jar"]

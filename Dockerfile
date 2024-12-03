FROM openjdk:21-jdk

# Copy the JAR file from the target directory of your project
COPY target/*.jar /app/app.jar

# Expose the port your application listens on (e.g., 8080)
EXPOSE 8081

# Set the entrypoint to run the JAR file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# Command for running Dockerfile  " docker build -t my-java-app . "
# Use official OpenJDK 17 as base image
FROM openjdk:17

# Copy your JAR file into the container
COPY /target/hostel_management-0.0.1-SNAPSHOT.jar /app/app.jar

# Set the default command to run your Java app
CMD ["java", "-cp", "/app/app.jar", "hostel_management.Main"]

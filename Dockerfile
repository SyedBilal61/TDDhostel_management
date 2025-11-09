# Use OpenJDK 17 official image with JDK
FROM eclipse-temurin:17-jdk


# Declare a build-time variable
ARG jarToCopy

# Copy your JAR into the container
COPY /target/$jarToCopy /app/app.jar

# Set default command to run your main class
CMD ["java", "-cp", "/app/app.jar", "hostel_management.Main"]

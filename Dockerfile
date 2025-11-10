# Use OpenJDK 17 official image with JDK
FROM eclipse-temurin:17-jdk

# Copy your fat JAR into the container
COPY target/hostel_management-0.0.1-SNAPSHOT-jar-with-dependencies.jar /app/app.jar

# Set default command to run your main class
CMD ["java", "-cp", "/app/app.jar", "hostel_management.Main", "mongodb"]

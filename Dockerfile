# Use OpenJDK 17 official image with JDK
FROM eclipse-temurin:17-jdk

# Copy your fat JAR into the container
COPY target/hostel_management-0.0.1-SNAPSHOT-jar-with-dependencies.jar /app/app.jar

# Download the wait-for-it script from GitHub and make it executable
ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /
RUN chmod +x /wait-for-it.sh

# Set default command to run your main class
CMD ["java", "-cp", "/app/app.jar", "hostel_management.Main", "mongodb"]

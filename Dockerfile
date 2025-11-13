# Use OpenJDK 17 official image
FROM eclipse-temurin:17-jdk

# Make sure /app exists and copy the fat JAR
WORKDIR /app
COPY target/hostel_management-0.0.1-SNAPSHOT-jar-with-dependencies.jar app.jar

# Download wait-for-it script
ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /
RUN chmod +x /wait-for-it.sh
# Default command to run the Java app and wait for MongoDB
CMD ["/wait-for-it.sh", "mongodb:27017", "--timeout=50", "--strict", "--", "java", "-jar", "/app/app.jar", "mongodb"]
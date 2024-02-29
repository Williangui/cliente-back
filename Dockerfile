# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the project files into the container at /app
COPY . /app

# Build the Spring Boot application using Maven
RUN ./mvnw clean package -DskipTests

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the Spring Boot application when the container launches
CMD ["java", "-jar", "target/clientes-1.0-SNAPSHOT.jar"]

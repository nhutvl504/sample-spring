# Stage 1: Build the application
FROM maven:amazoncorretto  AS build
WORKDIR /app

# Copy only the necessary files for dependency resolution and build caching
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# Copy the rest of the project files and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the production image with only the necessary files
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port (customize based on your Spring Boot app's port)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]


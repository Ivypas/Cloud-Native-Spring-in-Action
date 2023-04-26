# OpenJDK base image for the first stage
FROM eclipse-temurin:17 AS builder
WORKDIR workspace
# Builds argument specifying the location of the application JAR file in your project
ARG JAR_FILE=build/libs/*.jar
# Copies the application JAR file from the local machine into the image inside the “workspace” folder
COPY ${JAR_FILE} catalog-service.jar
# Extracts the layers from the archive applying the layered-JAR mode
RUN java -Djarmode=layertools -jar catalog-service.jar extract

# OpenJDK base image for the second stage
FROM eclipse-temurin:17
# Creates a “spring” user
RUN useradd spring
# Configures “spring” as the current user
USER spring
WORKDIR workspace
# Copies each JAR layer from the first stage to the second stage inside the “workspace” folder
COPY --from=builder workspace/dependencies/ ./
COPY --from=builder workspace/spring-boot-loader/ ./
COPY --from=builder workspace/snapshot-dependencies/ ./
COPY --from=builder workspace/application/ ./
# Uses the Spring Boot Launcher to start the application from the layers rather than an uber-JAR
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]

# Ubuntu base image with the Eclipse Temurin distribution of the JRE pre-installed
#FROM eclipse-temurin:17
# Changes the current working directory to “workspace”
#WORKDIR workspace
# Builds argument specifying the location of the application JAR file in your project
#ARG JAR_FILE=build/libs/*.jar
# Copies the application JAR file from the local machine into the image
#COPY ${JAR_FILE} catalog-service.jar
# Sets the container entry point to run the application
#ENTRYPOINT ["java", "-jar", "catalog-service.jar"]
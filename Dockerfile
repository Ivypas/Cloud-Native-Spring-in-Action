# Ubuntu base image with the Eclipse Temurin distribution of the JRE pre-installed
FROM eclipse-temurin:17
# Changes the current working directory to “workspace”
WORKDIR workspace
# Builds argument specifying the location of the application JAR file in your project
ARG JAR_FILE=build/libs/*.jar
# Copies the application JAR file from the local machine into the image
COPY ${JAR_FILE} catalog-service.jar
# Sets the container entry point to run the application
ENTRYPOINT ["java", "-jar", "catalog-service.jar"]
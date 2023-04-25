# (Read-only layers) 1st layer: Bases the new image on the official image for Ubuntu, version 22.04
FROM ubuntu:22.04

# (Read-only layers) 2nd layer: Installs the JRE using familiar bash commands
RUN apt-get update && apt-get install -y default-jre

# (Read-only layers) 3rd layer: Defines the execution entry point for the running container
ENTRYPOINT ["java", "--version"]
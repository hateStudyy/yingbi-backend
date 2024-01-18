# Docker 镜像构建
FROM maven:3.8.1-jdk-8-slim as builder

# Copy local code to the container image.
WORKDIR /app
ADD yubi-backend-0.0.1-SNAPSHOT.jar .

# Run the web service on container startup.
CMD ["java","-jar","/app/yingbi-backend-0.0.1-SNAPSHOT.jar","--spring.profiles.active=prod"]
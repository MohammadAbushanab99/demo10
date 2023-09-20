# Use an openjdk:17-alpine base image
# Use an openjdk:17-alpine base image
FROM openjdk:17-alpine

# Install Docker Compose
#RUN apk update && apk add --no-cache docker-compose
RUN apk update && apk add docker && apk --no-cache add docker-compose
# Copy your Spring Boot application JAR file into the container
COPY ./target/demo10-0.0.1-SNAPSHOT.jar demo10-0.0.1-SNAPSHOT.jar
COPY docker-compose.yml docker-compose.yml
COPY start-containers.sh start-containers.sh

RUN chmod +x start-containers.sh



# Start your Spring Boot application (modify the JAR file name if necessary)
CMD ["sh", "-c", "java -jar demo10-0.0.1-SNAPSHOT.jar"]


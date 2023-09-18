# Use an openjdk:17-alpine base image
FROM openjdk:17-alpine

# Install wget using apk
RUN apk update && apk add wget

# Copy your JAR file into the container
COPY ./target/demo10-0.0.1-SNAPSHOT.jar demo10-0.0.1-SNAPSHOT.jar

# Your CMD instruction remains the same
CMD ["sh", "-c", "java -jar demo10-0.0.1-SNAPSHOT.jar"]

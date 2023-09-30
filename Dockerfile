FROM openjdk:17-alpine

RUN apk update && apk add docker && apk --no-cache add docker-compose

COPY ./target/demo10-0.0.1-SNAPSHOT.jar demo10-0.0.1-SNAPSHOT.jar
COPY docker-compose.yml docker-compose.yml

CMD ["sh", "-c", "java -jar demo10-0.0.1-SNAPSHOT.jar"]


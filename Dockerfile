FROM openjdk:17

COPY ./target/demo10-0.0.1-SNAPSHOT.jar demo10-0.0.1-SNAPSHOT.jar

CMD ["sh", "-c","java -jar demo10-0.0.1-SNAPSHOT.jar"]


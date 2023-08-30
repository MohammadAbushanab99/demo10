FROM openjdk:17

COPY ./target/demo10-0.0.1-SNAPSHOT.jar demo10-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "demo10-0.0.1-SNAPSHOT.jar"]





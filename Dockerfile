FROM openjdk:17

COPY ./target/demo10-0.0.1-SNAPSHOT.jar demo10-0.0.1-SNAPSHOT.jar

EXPOSE 9998

CMD ["java", "-jar", "demo10-0.0.1-SNAPSHOT.jar"]





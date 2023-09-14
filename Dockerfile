FROM ubuntu:18.04

LABEL maintainer="Bibin Wilson <bibinwilsonn@gmail.com>"

RUN apt-get update && \
    apt-get -qy full-upgrade && \
    apt-get install -qy curl && \
    apt-get install -qy curl && \
    curl -sSL https://get.docker.com/ | sh && \
    apt-get install -qy openjdk-17-jdk

COPY ./target/demo10-0.0.1-SNAPSHOT.jar demo10-0.0.1-SNAPSHOT.jar

CMD ["sh", "-c", "dockerd-entrypoint.sh & java -jar demo10-0.0.1-SNAPSHOT.jar"]


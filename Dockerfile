FROM maven:alpine

COPY ./ ./

EXPOSE 8080

RUN mvn package
ENTRYPOINT mvn jetty:run
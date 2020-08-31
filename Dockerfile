FROM maven:alpine

COPY ./ ./
EXPOSE 8080

ENTRYPOINT mvn jetty:run
FROM maven:alpine

COPY ./ ./

EXPOSE 8080
RUN mvn install

ENTRYPOINT mvn jetty:run
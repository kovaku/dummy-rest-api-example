FROM maven:3.6-jdk-11-slim AS builder

COPY . /build/.

WORKDIR /build

RUN mvn clean verify

FROM openjdk:11.0.4-slim AS service

EXPOSE 8080

COPY --from=builder /build/target/app.jar .

ENV JAVA_OPTS=""

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar app.jar" ]

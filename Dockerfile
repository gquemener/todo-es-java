FROM eclipse-temurin:19-jdk-alpine

COPY src /app
COPY pom.xml /app
COPY mvnw /app
COPY .mvn /app

RUN mkdir -p /home/www/.m2
RUN chown -R 1000:1000 /home/www
ENV HOME=/home/www

WORKDIR /app

CMD ./mvnw spring-boot:run

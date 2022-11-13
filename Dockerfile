FROM eclipse-temurin:19-jdk-alpine

ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.9.0/wait /usr/bin/wait
RUN chmod +x /usr/bin/wait

COPY src /app
COPY pom.xml /app
COPY mvnw /app
COPY .mvn /app

RUN mkdir -p /home/www/.m2
RUN chown -R 1000:1000 /home/www
ENV HOME=/home/www

WORKDIR /app

CMD sh -c "/usr/bin/wait && ./mvnw spring-boot:run"

FROM maven:3.9.6-eclipse-temurin-17
WORKDIR /backend
COPY . .
RUN mvn clean install

CMD mvn spring-boot:run

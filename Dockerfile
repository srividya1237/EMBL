FROM maven:3.6.3-openjdk-17 AS build

#Set working directory
RUN mkdir /app

WORKDIR /app

COPY ./src /app/src

COPY ./pom.xml /app

COPY ./settings.xml /app

RUN mvn -s settings.xml clean install

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/Ontology-0.0.1-SNAPSHOT.jar ./Ontology-0.0.1-SNAPSHOT.jar

EXPOSE 8080
CMD ["java", "-jar", "./Ontology-0.0.1-SNAPSHOT.jar"]

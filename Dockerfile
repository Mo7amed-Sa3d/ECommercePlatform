FROM maven:3.9.11-eclipse-temurin-21-alpine AS build

WORKDIR /app

COPY pom.xml .

RUN mvn -q -e -DskipTests dependency:resolve dependency:resolve-plugins

COPY src ./src
RUN mvn -q -e -DskipTests package

#Runtime

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

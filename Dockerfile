# Stage 1: Build and test with Java 21
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /workspace

COPY pom.xml .
COPY src ./src

RUN mvn -B clean package

# Stage 2: Run with a Java 21 JRE
FROM eclipse-temurin:21-jre

WORKDIR /app

RUN groupadd --system --gid 10001 app \
    && useradd --system --uid 10001 --gid 10001 \
       --home-dir /app --shell /usr/sbin/nologin app \
    && mkdir -p /app/data/uploads \
    && chown -R 10001:10001 /app/data

COPY --from=build /workspace/target/*.jar /app/app.jar

USER 10001:10001

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

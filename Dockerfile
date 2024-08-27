FROM maven:3.9.8-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY . /app/.
RUN mvn -f /app/pom.xml clean package -Dmaven.test.skip=true

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/*.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/*.jar"]

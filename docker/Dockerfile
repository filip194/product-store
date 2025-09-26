FROM maven:3.9.4-eclipse-temurin-21 AS builder
WORKDIR /workspace
COPY . /workspace
RUN mvn -f pom.xml -B -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /workspace/target/product-store-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=postgresql", "app.jar"]

# Build stage
# ====== Stage 1: Build the JAR ======
FROM maven:3.9.7-eclipse-temurin-17 AS build
WORKDIR /workspace/app

# Copy pom.xml and download dependencies first (better caching)
COPY pom.xml .

RUN mvn -q -B dependency:go-offline

# Copy source code and build
#RUN mvn -q -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -B clean package -DskipTests

# ====== Stage 2: Run the app ======
# Runtime stage
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

RUN adduser -D appuser

USER appuser

COPY --from=build /workspace/app/target/*.jar app.jar

EXPOSE 8080
# Use env vars for JVM options if needed
ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
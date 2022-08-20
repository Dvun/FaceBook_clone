FROM maven:3.8.5-openjdk-17-slim AS MAVEN_BUILD
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn -B -U -e clean verify


FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/*.jar /app
ENTRYPOINT ["java", "-jar", "*.jar"]
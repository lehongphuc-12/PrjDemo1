FROM maven:3.9.6-eclipse-temurin-17 as build

# Copy source code và build
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests

# Dùng Tomcat làm server chạy WAR
FROM tomcat:10.1-jdk17-temurin

RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

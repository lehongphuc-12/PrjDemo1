# Stage 1: Build WAR bằng Maven
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Copy WAR vào Tomcat 10.1
FROM tomcat:10.1.24-jdk17-temurin
RUN rm -rf /usr/local/tomcat/webapps/ROOT
COPY --from=builder /app/target/demo1_1-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]

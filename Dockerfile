# Use a multi-stage build to create a lightweight image
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Use a lightweight base image for the final stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Add necessary environment variables for MySQL and other dependencies
#ENV PRODUCT_SERVICE_DB_URL=jdbc:mysql://mysql:3306/productdb
#ENV PRODUCT_SERVICE_DB_USER=root
#ENV PRODUCT_SERVICE_DB_PASSWORD=root
#ENV PRODUCT_SERVICE_PORT_NUMBER=8080
ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysql-productservice:3306/productdb
ENV SPRING_DATASOURCE_USERNAME=user
ENV SPRING_DATASOURCE_PASSWORD=root
ENV SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver

ENV SPRING_JPA_SHOW_SQL=true
ENV SPRING_APPLICATION_NAME=product-service
ENV SERVER_PORT=8080
ENV SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.MySQLDialect
# JWT authentication settings
ENV SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_JWK_SET_URI=http://USER-SERVICE/.well-known/jwks.json
ENV SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI=http://USER-SERVICE:3030


# Disable management metrics system
ENV MANAGEMENT_METRICS_SYSTEM_ENABLED=false

ENV EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://host.docker.internal:8761/eureka/

# Configure service discovery for microservices communication
ENTRYPOINT ["java", "-jar", "app.jar"]
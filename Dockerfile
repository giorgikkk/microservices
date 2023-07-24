FROM openjdk:18.0-jdk-oracle
WORKDIR /insurance
COPY car-insurance-web/target/*.jar insurance.jar
CMD ["java", "-jar", "insurance.jar"]
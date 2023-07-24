FROM openjdk:17-ea-3-jdk-oracle
WORKDIR /insurance
COPY car-insurance-web/target/*.jar insurance.jar
CMD ["java", "-jar", "insurance.jar"]
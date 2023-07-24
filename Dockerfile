FROM openjdk:18.0-jdk-oracle
WORKDIR /justice
COPY justice-ministry-web/target/*.jar justice.jar
CMD ["java", "-jar", "justice.jar"]
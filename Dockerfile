FROM openjdk:17-ea-3-jdk-oracle
WORKDIR /justice
COPY justice-ministry-web/target/*.jar justice.jar
CMD ["java", "-jar", "justice.jar"]
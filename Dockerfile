FROM openjdk:17-ea-3-jdk-oracle
WORKDIR /agency
COPY service-agency-web/target/*.jar agency.jar
CMD ["java", "-jar", "agency.jar"]

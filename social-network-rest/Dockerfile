FROM openjdk:11-jdk-slim

ADD target/social-network-rest-0.1-SNAPSHOT.jar social-network.jar

ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000","-jar","social-network.jar"]

EXPOSE 8081
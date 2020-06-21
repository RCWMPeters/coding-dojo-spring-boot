FROM openjdk:8-jdk-alpine
ADD /target/springbootweather.jar springbootweather.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "springbootweather.jar"]
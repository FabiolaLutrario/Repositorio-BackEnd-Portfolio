#FROM amazoncorretto:17-alpine-jdk
FROM amazoncorretto:8-alpine-jdk
#FROM amazoncorretto:17-alpine
MAINTAINER FabiolaLutrario
COPY target/portfolio-0.0.1-SNAPSHOT.jar portfolio-app.jar
ENTRYPOINT ["java","-jar","/portfolio-app.jar"]
#EXPOSE 8080
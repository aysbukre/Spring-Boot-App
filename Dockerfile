FROM openjdk:17

COPY questapp-0.0.1-SNAPSHOT.jar questapp.jar

ENTRYPOINT ["java","-jar","/questapp.jar"]
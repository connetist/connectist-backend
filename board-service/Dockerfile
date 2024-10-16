FROM openjdk:17
CMD ["./gradlew", "clean", "build"]
VOLUME /tmp
ARG JAR_FILE=build/libs/*-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar
#EXPOSE 9004
ENTRYPOINT ["java","-jar","/app.jar" ,"--spring.profiles.active=prod"]
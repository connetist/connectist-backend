FROM openjdk:17
CMD ["./gradlew", "clean", "build"]
VOLUME /tmp
ARG JAR_FILE=build/libs/*-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
FROM gradle:jdk17-alpine as build
COPY . /home/gradle
COPY build.gradle /home/gradle
RUN gradle clean build

FROM eclipse-temurin:17-jdk-alpine
COPY . /tmp
EXPOSE 5000

COPY --from=build /home/gradle/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
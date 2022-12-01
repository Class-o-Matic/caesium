FROM openjdk:17
EXPOSE 8080:8080
RUN mkdir /app
COPY ./build/libs/app.jar /app/ktor-app.jar
ENTRYPOINT [ "java", "-jar", "/app/ktor-app.jar" ]
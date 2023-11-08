FROM eclipse-temurin:17
LABEL maintainer="RJ"
COPY build/libs/*.jar app.jar
CMD ["java","-jar","/app.jar"]
EXPOSE 8080
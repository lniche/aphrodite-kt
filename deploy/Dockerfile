FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY ./build/libs/aphrodite-kt-1.0.0.jar  /app/api.jar

EXPOSE 8000

CMD ["java","-jar","/app/api.jar"]

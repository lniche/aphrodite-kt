FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY ./build/libs/aphrodite-ktor-all.jar  /app/api.jar

EXPOSE 8000

CMD ["java","-jar","/app/api.jar","-port=8000"]

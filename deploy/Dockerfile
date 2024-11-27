FROM eclipse-temurin:21

WORKDIR /app

COPY ./build/libs/aphrodite-ktor-all.jar  /app/api.jar

EXPOSE 8000

ENTRYPOINT ["java","-jar","/app/api.jar","-port=8000"]

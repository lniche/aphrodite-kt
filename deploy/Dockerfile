FROM eclipse-temurin:21

WORKDIR /app

COPY ./build/libs/aphrodite-kt-1.0.0.jar  /app/api.jar

EXPOSE 8000

ENTRYPOINT ["java","-jar","/app/api.jar"]

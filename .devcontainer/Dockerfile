FROM openjdk:21-jdk

WORKDIR /workspace

ARG GRADLE_VERSION=8.10.2
RUN wget -q https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip && \
    unzip gradle-${GRADLE_VERSION}-bin.zip -d /opt && \
    rm gradle-${GRADLE_VERSION}-bin.zip

ENV GRADLE_HOME /opt/gradle-${GRADLE_VERSION}
ENV PATH $GRADLE_HOME/bin:$PATH

COPY . .

EXPOSE 8000

CMD ["./gradlew", "bootRun"]

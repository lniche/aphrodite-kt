FROM openjdk:21-jdk

WORKDIR /workspace

RUN apt-get update && \
    apt-get install -y \
    gradle \
    git \
    curl \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

COPY . .

EXPOSE 8000

CMD ["/bin/bash"]

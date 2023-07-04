FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY . /app
RUN chmod +x gradlew
RUN ./gradlew clean build
EXPOSE 8080
CMD ["./gradlew", "build"]

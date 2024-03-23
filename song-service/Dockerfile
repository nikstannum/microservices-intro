FROM openjdk:17-alpine
LABEL maintaineer="Semeniuk N.L."
WORKDIR /app
COPY ./build/libs/song-service-0.0.1-SNAPSHOT.jar /app/
RUN apk update && apk add --no-cache bash
ENTRYPOINT ["java", "-jar"]
CMD ["song-service-0.0.1-SNAPSHOT.jar"]

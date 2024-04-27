FROM openjdk:17-alpine
LABEL maintaineer="Semeniuk N.L."
WORKDIR /app
COPY ./build/libs/resource-service-0.0.1-SNAPSHOT.jar /app/
RUN apk update && apk add --no-cache bash
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev"]
CMD ["resource-service-0.0.1-SNAPSHOT.jar"]
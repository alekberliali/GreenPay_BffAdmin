FROM openjdk:17

WORKDIR /app

COPY build/libs/bff-admin-back-0.0.1-SNAPSHOT.jar /app/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/bff-admin-back-0.0.1-SNAPSHOT.jar"]
CMD [""]






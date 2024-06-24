FROM openjdk:17-oracle

WORKDIR /app

COPY target/Assembly-Language-0.0.1-SNAPSHOT.jar /app/assembly-language.jar

EXPOSE 8080

CMD ["java", "-jar", "assembly-language.jar"]
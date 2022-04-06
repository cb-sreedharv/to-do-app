FROM 127322177288.dkr.ecr.us-west-1.amazonaws.com/cb-image:latest
ADD target/ToDo-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar","/app.jar"]

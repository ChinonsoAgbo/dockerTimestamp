FROM openjdk:17
EXPOSE 7070

# Specify a working directory
WORKDIR /app

# Copy the JAR file to the working directory
COPY ./build/libs/dockertimeregister-1.0-SNAPSHOT.jar /app/dockertimeregister.jar

# Set the entry point
ENTRYPOINT ["java", "-jar", "dockertimeregister.jar"]
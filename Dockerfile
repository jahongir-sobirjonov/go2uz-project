FROM openjdk:17
EXPOSE 8080
ARG JAR_FILE=target/go2uz-project-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} go2uz-project.jar
ENTRYPOINT ["java","-jar","/go2uz-project.jar"]

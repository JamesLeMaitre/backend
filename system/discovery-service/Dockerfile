FROM openjdk:20-jdk
WORKDIR /opt
ENV PORT 8761
EXPOSE 8761
COPY target/*.jar /opt/discovery.jar
ENTRYPOINT exec java $JAVA_OPTS -jar discovery.jar
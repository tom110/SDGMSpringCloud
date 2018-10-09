FROM localhost:5000/jdk8:slim

VOLUME /tmp
ADD auth-service-1.0-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
#EXPOSE 8761
ENV JAVA_OPTS=""

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
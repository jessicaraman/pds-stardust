FROM openjdk:11-jre-slim

ARG profile=dev
ENV spring_profiles_active=$profile

COPY stardustCA.pem stardustCA.pem
COPY build/libs/*.jar app.jar

RUN keytool -import -alias stardustCA -keystore $JAVA_HOME/lib/security/cacerts -file stardustCA.pem -storepass changeit -noprompt

ENTRYPOINT ["java","-jar","/app.jar"]

EXPOSE 8082
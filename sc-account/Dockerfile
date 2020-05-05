FROM openjdk:11-jre-slim

COPY src/main/resources/ssl/keystore.p12 keystore.p12
COPY stardustCA.pem stardustCA.pem
COPY build/libs/*.jar sc-account.jar

RUN keytool -import -alias stardustCA -keystore $JAVA_HOME/lib/security/cacerts -file stardustCA.pem -storepass changeit -noprompt

ENTRYPOINT ["java","-jar","/sc-account.jar"]

EXPOSE 8085
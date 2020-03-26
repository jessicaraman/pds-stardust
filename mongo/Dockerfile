FROM mongo:4.2.3

ARG dbUser
ARG dbPassword

ENV mongoUser=$dbUser
ENV mongoPassword=$dbPassword

COPY entrypoint.sh /docker-entrypoint-initdb.d/

ENV MONGO_INITDB_DATABASE=pds

EXPOSE 27017

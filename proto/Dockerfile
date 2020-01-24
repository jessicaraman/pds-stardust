FROM python:3.7.4-alpine

COPY requirements.txt /app/requirements.txt

WORKDIR /app

RUN apk update && apk add --no-cache postgresql-dev gcc make python3-dev libffi-dev musl-dev \
#&& pip3 install virtualenv \
#&& virtualenv env \
#&& source env/bin/activate \
&& pip3 install -r requirements.txt

COPY src src

COPY entrypoint.sh entrypoint.sh
COPY manage.py manage.py
COPY alembic alembic
COPY tests tests
COPY Dockerfile Dockerfile
COPY docker-compose.yml docker-compose.yml

EXPOSE 5000

RUN ["chmod", "u+x",  "./entrypoint.sh"]

ENTRYPOINT ["sh", "entrypoint.sh"]
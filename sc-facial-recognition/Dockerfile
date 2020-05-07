FROM python:3.8

# Create app directory
WORKDIR /app

# Install app dependencies
COPY requirement.txt ./

RUN pip install -r requirement.txt

# Bundle app source
COPY src /app

EXPOSE 8080

CMD ["flask","run"]
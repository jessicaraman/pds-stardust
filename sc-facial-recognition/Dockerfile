FROM python:3.8

# Create app directory
WORKDIR /app

# Copy app sources
COPY . .

# Install app dependencies
RUN pip3 install setuptools==45 firebase-admin opencv-python imutils sklearn && \
    pip3 install -r requirements.txt

EXPOSE 5000
CMD ["python3","sc-facial-recognition.py","run"]
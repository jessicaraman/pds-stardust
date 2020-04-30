FROM python:3.8

# Create app directory
WORKDIR /app

# Install app dependencies
COPY requirement.txt ./

RUN pip install -r requirements.txt

# Bundle app source
COPY src /app

EXPOSE 8080
CMD ["python" ,"recognize_video.py" ,"--detector", "face_detection_model", "--embedding-model", "openface_nn4.small2.v1.t7", "--recognizer", "output/recognizer.pickle", "--le output/le.pickle"]
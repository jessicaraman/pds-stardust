FROM python:3.8

# Create and defining app directory
WORKDIR /app

# Copy app sources
COPY . .

# Trick OS not to use default X server
RUN unset DISPLAY XAUTHORITY
# Install Virtual X Server
RUN apt-get update && apt-get install -y xvfb
# Make run.sh script executable
RUN chmod a+x run.sh
# Install python requirements
RUN pip3 install setuptools==45 firebase-admin opencv-python imutils sklearn
RUN pip3 install -r requirements.txt

# Expose server port
EXPOSE 5000

# Run start script
CMD ["/bin/sh", "/app/run.sh"]
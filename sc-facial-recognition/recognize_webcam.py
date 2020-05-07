# USAGE
# python recognize_webcam.py --detector face_detection_model --embedding-model openface_nn4.small2.v1.t7 --recognizer output/recognizer.pickle --le output/le.pickle

from imutils.video import VideoStream
from configuration import globalconfig as cfg
from recognition_process.modeler import Modeler
from argument.argument_settings import ArgumentSettings
from cache_manager.cache import Cache
import numpy as np
import imutils
import logging
import time
import cv2

# construct the argument parser and parse the arguments
logging.debug('Construct arguments')
argument = ArgumentSettings()
args = argument.args

# load serialized face detector, embedding and recognition with label encoder
logging.debug('Modeling detection, embedding and recognition')
modeler = Modeler(args)

# initialize the video stream, then allow the camera sensor to warm up

logging.debug('Starting video stream')
vs = VideoStream(src=0).start()
time.sleep(2.0)

# loop over frames from the video file stream
while True:

    # grab the frame from the threaded video stream
    logging.debug('Read stream')
    frame = vs.read()

    # resize the frame to have a width of 600 px while, maintaining the aspect ratio and grab the image, dimensions
    frame = imutils.resize(frame, width=600)
    (h, w) = frame.shape[:2]

    # construct a blob from the image
    imageBlob = cv2.dnn.blobFromImage(
        cv2.resize(frame, (300, 300)), 1.0, (300, 300),
        (104.0, 177.0, 123.0), swapRB=False, crop=False)

    # apply OpenCV's deep learning-based face detector to localize
    # faces in the input image
    modeler.detector.setInput(imageBlob)
    detections = modeler.detector.forward()

    # loop over the detections
    for i in range(0, detections.shape[2]):
        # extract the confidence (i.e., probability) associated with
        # the prediction
        confidence = detections[0, 0, i, 2]

        # filter out weak detections
        if confidence > cfg.confidence:
            # compute the (x, y)-coordinates of the bounding box for
            # the face
            box = detections[0, 0, i, 3:7] * np.array([w, h, w, h])
            (startX, startY, endX, endY) = box.astype("int")

            # extract the face ROI
            face = frame[startY:endY, startX:endX]
            (fH, fW) = face.shape[:2]

            # ensure the face width and height are sufficiently large
            if fW < 20 or fH < 20:
                continue

            # construct a blob and the 128-d vector
            faceBlob = cv2.dnn.blobFromImage(face, 1.0 / 255,
                                             (96, 96), (0, 0, 0), swapRB=True, crop=False)
            modeler.embedder.setInput(faceBlob)
            vec = modeler.embedder.forward()

            # perform classification to recognize the face
            preds = modeler.recognizer.predict_proba(vec)[0]
            j = np.argmax(preds)
            proba = preds[j]
            name = modeler.le.classes_[j]

            if(name!="unknown"):
                c = Cache()
                c.checking_cache(name,proba)

            # draw the bounding box of the face along with the
            # associated probability
            text = "{}: {:.2f}%".format(name, proba * 100)
            y = startY - 10 if startY - 10 > 10 else startY + 10
            cv2.rectangle(frame, (startX, startY), (endX, endY),
                          (0, 0, 255), 2)
            cv2.putText(frame, text, (startX, y),
                        cv2.FONT_HERSHEY_SIMPLEX, 0.45, (0, 0, 255), 2)

    # show the output frame
    cv2.imshow("Frame", frame)
    key = cv2.waitKey(1) & 0xFF

    # if the `q` key was pressed, break from the loop
    if key == ord("q"):
        break

cv2.destroyAllWindows()
vs.stop()

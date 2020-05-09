import logging
import time

import cv2
import imutils
import numpy as np
from imutils.video import VideoStream
import requests
from configuration import globalconfig as cfg
from firebase.firebase_manager.firebase_service import sendnotificationto
from recognition_process.modeler import Modeler
from web.request import get_token


# Webcam&Phone
class Webcam:

    ### Construct the argument parser and parse the arguments
    def __init__(self, trainModel):
        logging.info('Load construct arguments')
        # load serialized face detector, embedding and recognition with label encoder
        logging.info('Modeling detection, embedding and recognition')
        self.modeler = Modeler()
        self.trainModel = trainModel
        le = self.trainModel.getLabelEncoder()
        self.active = "true"

    ### Run process webcam
    def run(self,type):
        # Firebase service user tab already notified
        arr = []
        # initialize the video stream, then allow the camera sensor to warm up
        logging.info('Starting video stream')
        if type=="webcam":
            vs = VideoStream(src=0).start()
            time.sleep(2.0)
            self.process_analyze_cam(vs, arr, type)
            cv2.destroyAllWindows()
            vs.stop()
        elif type=="phone":
            url=cfg.streamurl
            time.sleep(2.0)
            self.process_analyze_cam(url, arr, type)
            cv2.destroyAllWindows()


    ### Launching process analyze cam with VideoStream
    def process_analyze_cam(self, vs, arr,type):
        # loop over frames from the video file stream
        while self.active == "true":
            # grab the frame from the threaded video stream
            logging.info('Read stream')
            if type=="webcam":
                frame = vs.read()
            elif type=="phone":
                img_resp = requests.get(vs)
                img_arr = np.array(bytearray(img_resp.content), dtype=np.uint8)
                img = cv2.imdecode(img_arr, -1)
                frame = img
            # resize the frame to have a width of 600 px while, maintaining the aspect ratio and grab the image, dimensions
            frame = imutils.resize(frame, width=600)
            (h, w) = frame.shape[:2]
            # construct a blob from the image
            imageBlob = cv2.dnn.blobFromImage(
                cv2.resize(frame, (300, 300)), 1.0, (300, 300),
                (104.0, 177.0, 123.0), swapRB=False, crop=False)
            # apply OpenCV's deep learning-based face detector to localize
            # faces in the input image
            self.modeler.detector.setInput(imageBlob)
            detections = self.modeler.detector.forward()
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
                    self.modeler.embedder.setInput(faceBlob)
                    vec = self.modeler.embedder.forward()
                    # perform classification to recognize the face
                    preds = self.modeler.recognizer.predict_proba(vec)[0]
                    j = np.argmax(preds)
                    proba = preds[j]
                    name = self.modeler.le.classes_[j]
                    # Verify if the user is already notified or not
                    self.verify_token_notified(name, arr)
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

    ### Verify if the token is already sent to the customer identified
    def verify_token_notified(self, name, arr):
        if name != "unknown":
            logging.info('Detected username : ' + name)
            tmp = {"name": name, "notified": "true"}
            if tmp in arr:
                logging.info("The user " + name + " is already notified")
            else:
                token = get_token(name)
                if token != 'none' and token != name:
                    sendnotificationto(token)
                    arr.append(tmp)
                else:
                    logging.info("No token found for " + name + ", cannot send notification")
        else:
            logging.info("No token for unknown")
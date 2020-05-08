import logging
import os
import pickle

import cv2

from configuration import globalconfig as cfg


# Modeler
class Modeler:
    def __init__(self):
        # load our serialized face detector from disk
        logging.info('Loading face detector')
        protoPath = os.path.sep.join([cfg.detector, "deploy.prototxt"])
        modelPath = os.path.sep.join([cfg.detector, cfg.format_model])
        self.detector = cv2.dnn.readNetFromCaffe(protoPath, modelPath)

        # load our serialized face embedding model from disk
        logging.info('Loading face recognizer')
        self.embedder = cv2.dnn.readNetFromTorch(cfg.embedder)
        # load the actual face recognition model along with the label encoder
        self.recognizer = pickle.loads(open(cfg.recognizer, "rb").read())
        self.le = pickle.loads(open(cfg.le, "rb").read())
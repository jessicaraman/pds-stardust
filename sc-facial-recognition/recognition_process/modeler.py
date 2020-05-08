import logging
import os
import pickle

import cv2

from configuration import globalconfig as cfg


class Modeler:
    def __init__(self):
        # load our serialized face detector from disk
        logging.debug('Loading face detector')
        protoPath = os.path.sep.join([cfg.detector, "deploy.prototxt"])
        modelPath = os.path.sep.join([cfg.detector,
                                      "res10_300x300_ssd_iter_140000.caffemodel"])
        self.detector = cv2.dnn.readNetFromCaffe(protoPath, modelPath)

        # load our serialized face embedding model from disk
        logging.debug('Loading face recognizer')
        # don't know why but when i put cfg.embedding_model which contains the same string, it doesnt work
        self.embedder = cv2.dnn.readNetFromTorch("openface_nn4.small2.v1.t7")

        # load the actual face recognition model along with the label encoder
        self.recognizer = pickle.loads(open(cfg.recognizer, "rb").read())
        self.le = pickle.loads(open(cfg.le, "rb").read())

from configuration import globalconfig as cfg
import pickle
import cv2
import os
import logging

class Modeler:
    def __init__(self,args):
        # load our serialized face detector from disk
        logging.debug('Loading face detector')
        protoPath = os.path.sep.join([cfg.detector, "deploy.prototxt"])
        modelPath = os.path.sep.join([cfg.detector,
                                      "res10_300x300_ssd_iter_140000.caffemodel"])
        self.detector = cv2.dnn.readNetFromCaffe(protoPath, modelPath)

        # load our serialized face embedding model from disk
        logging.debug('Loading face recognizer')
        self.embedder = cv2.dnn.readNetFromTorch(args["embedding_model"])

        # load the actual face recognition model along with the label encoder
        self.recognizer = pickle.loads(open(cfg.recognizer, "rb").read())
        self.le = pickle.loads(open(cfg.le, "rb").read())
import logging
import pickle

# import the necessary packages
from sklearn.preprocessing import LabelEncoder
from sklearn.svm import SVC

from configuration import globalconfig as cfg

logging.basicConfig(level=logging.DEBUG)


### TrainModel : train the model used to accept the 128-d embeddings of the face and
### then produce the actual face recognition
class TrainModel:

    # Construct the argument parser and parse the arguments
    def __init__(self):
        # load the face embeddings
        logging.info("Loading face embeddings...")
        self.data = pickle.loads(open(cfg.embedding, "rb").read())
        # encode the labels
        logging.info("Encoding labels...")
        self.le = LabelEncoder()
        self.labels = self.le.fit_transform(self.data["names"])

    # Train the model
    def train(self):
        logging.info("Training model...")
        recognizer = SVC(C=1.0, kernel="linear", probability=True)
        recognizer.fit(self.data["embeddings"], self.labels)

        # write the actual face recognition model to disk
        f = open(cfg.recognizer, "wb")
        f.write(pickle.dumps(recognizer))
        f.close()

        # write the label encoder to disk
        f = open(cfg.le, "wb")
        f.write(pickle.dumps(self.le))
        f.close()

    def getLabelEncoder(self):
        return self.le
import argparse

class ArgumentSettings:
    def __init__(self):
        self.arg = argparse.ArgumentParser()
        self.arg.add_argument("-d", "--detector", required=True,
                        help="path to OpenCV's deep learning face detector")
        self.arg.add_argument("-m", "--embedding-model", required=True,
                        help="path to OpenCV's deep learning face embedding model")
        self.arg.add_argument("-r", "--recognizer", required=True,
                        help="path to model trained to recognize faces")
        self.arg.add_argument("-l", "--le", required=True,
                        help="path to label encoder")
        self.arg.add_argument("-c", "--confidence", type=float, default=0.5,
                        help="minimum probability to filter weak detections")
        self.args = vars(self.arg.parse_args())
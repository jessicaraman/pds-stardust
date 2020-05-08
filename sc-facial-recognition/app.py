import logging

from flask import Flask
import requests
from cache_manager.cache import Cache
from loader import Loader
from recognize_webcam import Webcam
from trainModel import TrainModel

app = Flask(__name__)
loader = ""
train_model = ""
webcam = ""

logging.basicConfig(level=logging.DEBUG)


@app.route('/')
def heartbeat():
    logging.info("Heartbeat API facial recognition /")
    return 'API facial recognition'


@app.route('/load_data')
def load_data():
    logging.info("Load_data /load_data")
    loader = Loader()
    loader.load_data()
    return 'loaded'


@app.route('/train_model')
def train_model():
    logging.info("Train_Model /train_model")
    train_model = TrainModel()
    train_model.train()
    return 'trained'


@app.route('/webcam_start')
def webcam_start():
    logging.info("Launching webcam... /webcam_start")
    train_model = TrainModel()
    train_model.train()
    webcam = Webcam(train_model)
    webcam.run()
    logging.info("Starting webcam... /webcam_start")
    return 'started'


@app.route('/clean')
def clean():
    logging.info("Delete cache... /clean")
    c = Cache()
    c.clean()
    return 'cleaned'


if __name__ == '__main__':
    # certificate and key files
    app.run(debug=True, ssl_context=('server.crt', 'facialrecognition.key'))

from flask import Flask

from cache_manager.cache import Cache
from recognize_webcam import Webcam
from trainModel import TrainModel

app = Flask(__name__)
loader = ""
train_model = ""
webcam = ""


@app.route('/')
def heartbeat():
    return 'API facial recognition'


@app.route('/load_data')
def load_data():
    loader.load_data()
    return 'loaded'


@app.route('/train_model')
def train_model():
    train_model.train()
    return 'trained'


@app.route('/webcam_start')
def webcam_start():
    train_model = TrainModel()
    train_model.train()
    webcam = Webcam(train_model)
    webcam.run()
    return 'started'


@app.route('/clean')
def clean():
    c = Cache()
    c.clean()
    return 'cleaned'


app.run()

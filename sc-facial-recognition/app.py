from flask import Flask
from cache_manager.cache import Cache
from loader import Loader
from trainModel import TrainModel
from recognize_webcam import Webcam

loader = Loader()

app = Flask(__name__)

@app.route('/')
def hello():
    return 'Hello Flask!'

@app.route('/load_data')
def load_data():
    loader.load_data()
    return 'loaded'

@app.route('/train_model')
def train_model():
    train_model = TrainModel()
    train_model.train()
    return 'trained'

@app.route('/webcam')
def webcam():
    train_model = TrainModel()
    train_model.train()
    webcam = Webcam(train_model)
    webcam.recognize()
    return 'started'

@app.route('/clean')
def clean():
    c = Cache()
    c.clean()
    return 'cleaned'

app.run()
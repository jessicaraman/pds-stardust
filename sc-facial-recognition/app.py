from flask import Flask
from cache_manager.cache import Cache
import os

app = Flask(__name__)

@app.route('/')
def hello():
    return 'Hello Flask!'

@app.route('/clean')
def train_model():
    c = Cache()
    c.clean()
    return 'cleaned'
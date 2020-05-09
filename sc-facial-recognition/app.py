import logging
import ssl

from flask import Flask

from cache_manager.cache import Cache
from configuration import globalconfig as cfg
from recognition_process.loader import Loader
from recognition_process.train_model import TrainModel
from recognition_process.webcam import Webcam

app = Flask(__name__)
loader = ""
train_model = ""
webcam = ""

logging.basicConfig(level=logging.DEBUG)


# Hearbeat
@app.route('/')
def heartbeat():
    logging.info("Heartbeat API facial recognition /")
    return 'API facial recognition'


# /load_data
@app.route('/load_data')
def load_data():
    logging.info("Load_data /load_data")
    loader = Loader()
    loader.load_data()
    return 'loaded'


# /train_model : update files
@app.route('/train_model')
def train_model():
    logging.info("/train_model Launching train model...")
    train_model = TrainModel()
    train_model.train()
    logging.info("/train_model Finished train model...")
    return 'trained'


# /webcam_start : starting process facial recognition
@app.route('/webcam_start')
def webcam_start():
    logging.info("/webcam_start Launching webcam...")
    train_model = TrainModel()
    train_model.train()
    webcam = Webcam(train_model)
    webcam.run()
    logging.info("/webcam_start Starting webcam...")
    return 'started'


# /clean :
@app.route('/clean')
def clean():
    logging.info("/clean Delete cache")
    c = Cache()
    c.clean()
    logging.info("/clean Cache deleted")
    return 'cleaned'


if __name__ == '__main__':
    context = ssl.SSLContext(ssl.PROTOCOL_TLSv1_2)
    context.verify_mode = ssl.CERT_REQUIRED
    context.load_verify_locations(cfg.ssl_ca_pem)
    context.load_verify_locations(cfg.ssl_certificate, cfg.ssl_certificate_key)
    app.run(debug=True, host="0.0.0.0", ssl_context=(cfg.ssl_certificate, cfg.ssl_certificate_key))

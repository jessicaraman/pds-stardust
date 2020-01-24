from flask import Flask
from flask_bcrypt import Bcrypt

from src.api.routes import blueprint
from src.config import config_by_name
from src.database import db

flask_bcrypt = Bcrypt()


def register_blueprints(app):
    app.register_blueprint(blueprint, url_prefix='')


def create_app(config_name):
    app = Flask(__name__)
    app.config.from_object(config_by_name[config_name])
    register_blueprints(app)
    db.init_app(app)
    flask_bcrypt.init_app(app)

    return app

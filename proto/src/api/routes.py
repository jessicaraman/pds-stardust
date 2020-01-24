from flask import Blueprint
from flask_restplus import Api

from .tweet_controller import api as tweet_ns
from .user_controller import api as user_ns

blueprint = Blueprint('api', __name__)

api = Api(blueprint,
          title='FLASK RESTPLUS API POC',
          version='1.0',
          description='PDS prototype'
          )

api.add_namespace(tweet_ns, path='/users/<user_id>/tweets')
api.add_namespace(user_ns, path='/users')
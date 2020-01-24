from flask_restplus import Namespace, fields
from ..tweets import tweetDto


class UserDto:
    api = Namespace('user', description='users namespace')
    user = api.model('user', {
        'id': fields.String(required=False, description='user id', max=64, readonly=True),
        'pseudo': fields.String(required=True, description='user pseudo', max=64),
        'tweets': fields.List(fields.Nested(tweetDto.TweetDto.tweet, skip_none=True), required=False, readonly=True)
    })

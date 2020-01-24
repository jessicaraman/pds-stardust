from flask_restplus import Namespace, fields


class TweetDto:
    api = Namespace('tweet', description='tweets namespace')
    tweet = api.model('tweet', {
        'id': fields.String(required=False, description='tweet id', max=64, readonly=True),
        'message': fields.String(required=True, description='tweet content', max=280),
    })

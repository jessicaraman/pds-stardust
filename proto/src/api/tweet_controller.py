from flask import request
from flask_restplus import Resource

from ..service.tweet_service import create_tweet, get_all_tweets, update_tweet, delete_tweet, find_tweet_by_id
from src.tweets.tweetDto import TweetDto

api = TweetDto.api
_tweet = TweetDto.tweet


@api.route('')
@api.param('user_id', 'Generated id of the user')
class TweetList(Resource):
    @api.doc('List tweets of a specific user')
    @api.marshal_list_with(_tweet, skip_none=True)
    def get(self, user_id):
        return get_all_tweets(user_id)

    @api.expect(_tweet, validate=True)
    @api.response(201, 'Tweet successfully created.')
    @api.doc('create a new tweet')
    @api.marshal_with(_tweet, skip_none=True)
    def post(self, user_id):
        return create_tweet(data=request.json, user_id=user_id)


@api.param('user_id', 'Generated id of the user')
@api.param('tweet_id', 'Generated id of the tweet')
@api.route('/<tweet_id>')
class TweetDetail(Resource):

    @api.response(200, 'tweet exist')
    @api.doc('tweet_detail')
    @api.marshal_with(_tweet, skip_none=True)
    def get(self, tweet_id, user_id):
        return find_tweet_by_id(tweet_id=tweet_id, user_id=user_id)

    @api.marshal_with(_tweet, skip_none=True)
    @api.response(204, 'tweet deleted')
    @api.doc('user_deletion')
    def delete(self, tweet_id, user_id):
        return delete_tweet(tweet_id=tweet_id, user_id=user_id)

    @api.expect(_tweet, validate=True)
    @api.marshal_with(_tweet, skip_none=True)
    @api.response(200, 'tweet updated')
    @api.doc('tweet_update')
    def patch(self, tweet_id, user_id):
        return update_tweet(tweet_id=tweet_id, user_id=user_id, data=request.json)

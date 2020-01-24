import uuid
import datetime

from src.database import db
from ..tweets.tweet import Tweet
from ..users.user import User

from werkzeug.exceptions import NotFound


def create_tweet(data, user_id):
    """ Method for tweet creation """

    if User.query.filter(User.id == user_id).first() is None:
        raise NotFound('User with the id {} doesn\'t exist'.format(user_id))

    tweet = Tweet(
        id=str(uuid.uuid4()),
        message=data['message'],
        creation_date=datetime.datetime.utcnow(),
        user_id=user_id
    )

    save_changes(tweet)

    return tweet, 201, {'location': '/users/{}/tweets/{}'.format(user_id, tweet.id)}


def get_all_tweets(user_id):
    """ Method for tweets listing of a user"""

    if User.query.filter(User.id == user_id).first() is None:
        raise NotFound('User with the id {} doesn\'t exist'.format(user_id))

    return Tweet.query.filter(Tweet.user_id == user_id).all(), 200


def find_tweet_by_id(tweet_id, user_id):
    """ Method for tweet detail """

    if User.query.filter(User.id == user_id).first() is None:
        raise NotFound('User with the id {} doesn\'t exist'.format(user_id))

    tweet = Tweet.query.filter(Tweet.id == tweet_id and Tweet.user_id == user_id).first()

    if tweet is None:
        raise NotFound('User with the id {} doesn\'t exist'.format(user_id))

    return tweet, 200


def delete_tweet(tweet_id, user_id):
    """ Method for tweet deletion """

    if User.query.filter(User.id == user_id).first() is None:
        raise NotFound('User with the id {} doesn\'t exist'.format(user_id))

    tweet = Tweet.query.filter(Tweet.id == tweet_id and Tweet.user_id == user_id).first()

    if tweet is None:
        raise NotFound('Tweet with the id {} doesn\'t exist'.format(tweet_id))

    db.session.delete(tweet)
    db.session.commit()

    return '', 204


def update_tweet(user_id, tweet_id, data):

    if User.query.filter(User.id == user_id).first() is None:
        raise NotFound('User with the id {} doesn\'t exist'.format(user_id))

    tweet = Tweet.query.filter(Tweet.id == tweet_id and Tweet.user_id == user_id).first()

    if tweet is None:
        raise NotFound('Tweet with the id {} doesn\'t exist'.format(tweet_id))

    tweet.message = data['message'],
    tweet.update_date = datetime.datetime.utcnow()

    save_changes(tweet)

    return tweet, 200


def save_changes(tweet):
    """ Method to save tweet data to database """

    db.session.add(tweet)
    db.session.commit()

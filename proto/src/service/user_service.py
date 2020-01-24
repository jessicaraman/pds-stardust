import uuid
import datetime

from src.database import db
from ..users.user import User

from werkzeug.exceptions import BadRequest, NotFound, Conflict


def create_user(data):
    """ Method for user creation """

    pseudo = data['pseudo']

    if User.query.filter(User.pseudo == pseudo).first():
        raise Conflict('User with pseudo {} already exist'.format(pseudo))

    user = User(
        id=str(uuid.uuid4()),
        pseudo=pseudo,
        creation_date=datetime.datetime.utcnow(),
    )

    save_changes(user)

    return user, 201, {'location': '/users/{}'.format(user.id)}


def get_all_users():
    """ Method for users listing """

    return User.query.all(), 200


def find_user_by_id(user_id):
    """ Method for user detail """

    user = User.query.filter(User.id == user_id).first()

    if user is None:
        raise NotFound('User with the id {} doesn\'t exist'.format(user_id))

    return user, 200


def delete_user(user_id):
    """ Method for user deletion """

    user = User.query.filter(User.id == user_id).first()

    if user is None:
        raise NotFound('User with the id {} doesn\'t exist'.format(user_id))

    db.session.delete(user)
    db.session.commit()

    return '', 204


def update_user(user_id, data):
    """ Method for user update """
    user = User.query.filter(User.id == user_id).first()

    if user is None:
        raise NotFound('User with the id {} doesn\'t exist'.format(user_id))

    pseudo = data['pseudo']

    if user.pseudo == pseudo:
        raise BadRequest('Cannot update user infos : User already have the pseudo -> {}'.format(pseudo))

    if User.query.filter(User.pseudo == pseudo).first():
        raise BadRequest('Cannot update user infos : A user with the pseudo {} already exist'.format(pseudo))

    user.pseudo = pseudo
    user.update_date = datetime.datetime.utcnow(),

    save_changes(user)

    return user, 200


def save_changes(user):
    """ Method to save user data to database """

    db.session.add(user)
    db.session.commit()

from flask import request
from flask_restplus import Resource
import socket 

from ..service.user_service import create_user, get_all_users, find_user_by_id, delete_user, update_user
from src.users.userDto import UserDto

api = UserDto.api
_user = UserDto.user


@api.route('')
class UserList(Resource):
    @api.doc('List all users')
    @api.response(200, 'Users obtained')
    # @api.marshal_list_with(_user)
    def get(self):
        host_name = socket.gethostname() 
        host_ip = socket.gethostbyname(host_name) 
        return host_ip, 200

    @api.expect(_user, validate=True)
    @api.response(409, 'A user with the same pseudo already exist')
    @api.doc('Create a new user')
    @api.marshal_with(_user, code=201, description='User successfully created', skip_none=True)
    def post(self):
        return create_user(data=request.json)


@api.param('user_id', 'The user id')
@api.route('/<user_id>')
class UserDetail(Resource):

    @api.response(200, 'User exist')
    @api.response(404, 'User is not found')
    @api.doc('Get user infos')
    @api.marshal_with(_user, skip_none=True)
    def get(self, user_id):
        return find_user_by_id(user_id)

    @api.marshal_with(_user, skip_none=True)
    @api.response(204, 'User successfully deleted')
    @api.response(404, 'User is not found')
    @api.doc('Delete a user')
    def delete(self, user_id):
        return delete_user(user_id)

    @api.expect(_user, validate=True)
    @api.marshal_with(_user, skip_none=True)
    @api.response(200, 'User successfully updated')
    @api.response(404, 'User is not found')
    @api.response(400, 'The new pseudo provided is already in use by the same or another user')
    @api.doc('Update a user infos')
    def patch(self, user_id):
        return update_user(user_id, request.json)

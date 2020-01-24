from urllib.parse import urljoin
from tests.test_util import post, get, patch, delete, wait_for_api


def test_create_user(wait_for_api):
    request_session, api_url = wait_for_api

    payload = {'pseudo': 'tests'}

    resp = post(urljoin(api_url, 'users'), payload)

    assert resp.status_code == 201, "User not successfully created"

    body = resp.json()

    assert body is not None
    assert body['id'] is not None
    assert body['pseudo'] == 'tests'


def test_list_user(wait_for_api):
    request_session, api_url = wait_for_api

    post(urljoin(api_url, 'users'), {'pseudo': 'tests'})

    resp = get(urljoin(api_url, 'users'))

    assert resp.status_code == 200, "Status code not equals"

    body = resp.json()

    assert body is not None
    assert len(body) == 1, "Expected one user"

    user = body[0]

    assert user is not None
    assert user['id'] is not None
    assert user['pseudo'] == 'tests'


def test_update_user(wait_for_api):
    request_session, api_url = wait_for_api

    user = post(urljoin(api_url, 'users'), {'pseudo': 'tests'}).json()

    payload = {'pseudo': 'updated'}

    resp = patch(urljoin(api_url, 'users/{}'.format(user['id'])), payload)

    assert resp.status_code == 200, "User not updated"

    body = resp.json()

    assert body is not None
    assert body['id'] is not None
    assert body['pseudo'] == 'updated'


def test_delete_user(wait_for_api):
    request_session, api_url = wait_for_api

    user = post(urljoin(api_url, 'users'), {'pseudo': 'tests'}).json()

    resp = delete(urljoin(api_url, 'users/{}'.format(user['id'])))

    assert resp.status_code == 204, "User not deleted"

    resp = get(urljoin(api_url, 'users'))

    body = resp.json()

    assert body is not None
    assert len(body) == 0, "Expected no user"


def test_detail_user(wait_for_api):
    request_session, api_url = wait_for_api

    user = post(urljoin(api_url, 'users'), {'pseudo': 'tests'}).json()

    resp = get(urljoin(api_url, 'users/{}'.format(user['id'])))

    assert resp.status_code == 200, "User not found"

    body = resp.json()

    assert body is not None
    assert body['id'] == user['id']
    assert body['pseudo'] == user['pseudo']


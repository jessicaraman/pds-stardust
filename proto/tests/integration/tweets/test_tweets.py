from urllib.parse import urljoin

from tests.test_util import post, get, patch, delete, wait_for_api


def test_create_tweet(wait_for_api):
    request_session, api_url = wait_for_api

    user = post(urljoin(api_url, 'users'), {'pseudo': 'tests'}).json()

    payload = {'message': 'My tests tweet !'}

    resp = post(urljoin(api_url, 'users/{}/tweets'.format(user['id'])), payload)

    assert resp.status_code == 201, "Tweet not successfully created"

    body = resp.json()

    assert body is not None
    assert user['id'] is not None
    assert body['message'] == 'My tests tweet !'


def test_ist_tweet(wait_for_api):
    request_session, api_url = wait_for_api

    user_id = post(urljoin(api_url, 'users'), {'pseudo': 'tests'}).json()['id']

    post(urljoin(api_url, 'users/{}/tweets'.format(user_id)), {'message': 'My tests tweet !'})

    resp = get(urljoin(api_url, 'users/{}/tweets'.format(user_id)))

    assert resp.status_code == 200, "Status code not equals"

    body = resp.json()

    assert body is not None
    assert len(body) == 1, "Expected one tweet"

    tweet = body[0]

    assert tweet is not None
    assert tweet['id'] is not None
    assert tweet['message'] == 'My tests tweet !'


def test_update_tweet(wait_for_api):
    request_session, api_url = wait_for_api

    user_id = post(urljoin(api_url, 'users'), {'pseudo': 'tests'}).json()['id']

    tweet_id = post(urljoin(api_url, 'users/{}/tweets'.format(user_id)), {'message': 'My tests tweet !'}).json()['id']

    payload = {'message': 'My updated tweet !'}

    resp = patch(urljoin(api_url, 'users/{}/tweets/{}'.format(user_id, tweet_id)), payload)

    assert resp.status_code == 200, "Tweet not updated"

    body = resp.json()

    assert body is not None
    assert body['id'] is not None
    assert body['message'] == 'My updated tweet !'


def test_delete_tweet(wait_for_api):
    request_session, api_url = wait_for_api

    user_id = post(urljoin(api_url, 'users'), {'pseudo': 'tests'}).json()['id']

    tweet_id = post(urljoin(api_url, 'users/{}/tweets'.format(user_id)), {'message': 'My tests tweet !'}).json()['id']

    resp = delete(urljoin(api_url, 'users/{}/tweets/{}'.format(user_id, tweet_id)))

    assert resp.status_code == 204, "Tweet not deleted"

    resp = get(urljoin(api_url, 'users/{}/tweets'.format(user_id)))

    body = resp.json()

    assert body is not None
    assert len(body) == 0, "Expected no tweet"

    resp = get(urljoin(api_url, 'users/{}'.format(user_id)))

    assert resp.status_code == 200, "User not found"

    body = resp.json()

    assert body is not None


def test_detail_tweet(wait_for_api):
    request_session, api_url = wait_for_api

    user_id = post(urljoin(api_url, 'users'), {'pseudo': 'tests'}).json()['id']

    tweet = post(urljoin(api_url, 'users/{}/tweets'.format(user_id)), {'message': 'My tests tweet !'}).json()

    resp = get(urljoin(api_url, 'users/{}/tweets/{}'.format(user_id, tweet['id'])))

    assert resp.status_code == 200, "Tweet not found"

    body = resp.json()

    assert body is not None
    assert body['id'] == tweet['id']
    assert body['message'] == tweet['message']
import json

import pytest
import requests
from requests.adapters import HTTPAdapter
from urllib3 import Retry

pytest_plugins = ["docker_compose"]

"""See -> https://github.com/pytest-docker-compose/pytest-docker-compose#use-wider-scoped-fixtures"""
@pytest.fixture(scope="function")
def wait_for_api(function_scoped_container_getter):
    """Wait for the service {app} to become responsive"""
    request_session = requests.Session()
    retries = Retry(total=5,
                    backoff_factor=0.1,
                    status_forcelist=[500, 502, 503, 504])
    request_session.mount('http://', HTTPAdapter(max_retries=retries))

    service = function_scoped_container_getter.get("app").network_info[0]
    api_url = "http://%s:%s/" % (service.hostname, service.host_port)
    assert request_session.get(api_url)
    return request_session, api_url


headers = {'Content-Type': 'application/json'}


def post(url, data):
    return requests.post(url, data=json.dumps(data, indent=4), headers=headers)


def get(url):
    return requests.get(url, headers=headers)


def patch(url, data):
    return requests.patch(url, data=json.dumps(data, indent=4), headers=headers)


def delete(url):
    return requests.delete(url, headers=headers)


def put(url, data):
    return requests.put(url, data=json.dumps(data, indent=4), headers=headers)

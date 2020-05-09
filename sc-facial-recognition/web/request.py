import logging

import requests

from configuration import globalconfig as cfg

logging.basicConfig(level=logging.DEBUG)


# getToken from sc-account api
def get_token(name):
    try:
        token = "none"
        customer_entity = {'username': name}
        response = requests.post(url=cfg.urlscaccountapi, json=customer_entity, verify=False);
        if response.status_code == 200:
            token = response.text
        else:
            logging.debug("No token found for " + name)
        return token
    except requests.exceptions.RequestException as e:
        return "Error: {}".format(e)
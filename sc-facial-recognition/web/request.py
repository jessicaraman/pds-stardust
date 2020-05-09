import logging

import requests

from configuration import globalconfig as cfg

logging.basicConfig(level=logging.DEBUG)


# getToken from sc-account api
def getToken(name):
    resp = "none"
    customerEntity = {'username': name}
    # sending get request and saving the response as response object
    r = requests.post(url=cfg.urlscaccountapi, json=customerEntity, verify=False);
    if (r.status_code == 200):
        resp = r.text  ### oui il a pas de token, c'est none par défaut
    else:
        logging.debug("No token found for " + name)

    return resp

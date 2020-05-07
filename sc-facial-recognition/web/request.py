# importing the requests library
import requests

def getToken(name):
    # api-endpoint
    URL = "http://account:8085/token"

    # location given here
    location = "delhi technological universcity"

    # defining a params dict for the parameters to be sent to the API
    PARAMS = {'address': location}
    customerEntity = { 'username' : name}
    # sending get request and saving the response as response object
    r = requests.post(url=URL, json=customerEntity);

    return r.text

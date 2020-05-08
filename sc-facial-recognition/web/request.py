# importing the requests library
import requests

# getToken from sc-account api
def getToken(name):
    resp = "none"
    # api-endpoint
    URL = "http://account:8085/token"
    #URL = "https://172.31.249.114:9990/api/account/";
    customerEntity = {'username': name}
    # sending get request and saving the response as response object
    r = requests.post(url=URL, json=customerEntity);
    if(r.status_code == 200):
        resp = r.text ### oui il a pas de token, c'est none par défaut
    else:
        print("no token for " + name)

    return resp

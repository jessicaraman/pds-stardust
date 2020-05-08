import firebase_admin
from firebase_admin import credentials
from firebase_admin import messaging

from configuration import globalconfig as cfg
import logging
cred = credentials.Certificate(cfg.firebasekey)
firebase_admin.initialize_app(cred)

logging.basicConfig(level=logging.DEBUG)

def sendnotificationto(token):
  logging.info("User's Token = " + token)

  message = messaging.Message(
    notification=messaging.Notification(
      title='EPISEN',
      body='Notification sent',
    ),
    data={
      'subject': 'IT',
      'place': '40',
    },
    token=token,
  )
  response = messaging.send(message)
  logging.info('Successfully sent message:', response)

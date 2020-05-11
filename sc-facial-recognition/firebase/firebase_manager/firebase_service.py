import logging

import firebase_admin
from firebase_admin import credentials
from firebase_admin import messaging

from configuration import globalconfig as cfg

cred = credentials.Certificate(cfg.firebase_key)
firebase_admin.initialize_app(cred)

logging.basicConfig(level=logging.DEBUG)

### FirebaseService : Handle notification fcm
class FirebaseService:

  # sendnotificationto : send notification to user
  def sendnotificationto(self, token):
    logging.info("User's Token = " + token)
    self.message = messaging.Message(
      notification=messaging.Notification(
        title='EPISEN',
        body='PDS facial recognition notification!',
      ),
      data={
        'subject': 'IT',
        'place': '40',
      },
      token=token,
    )
    self.response = messaging.send(self.message)
    logging.info('Response message:', self.response)

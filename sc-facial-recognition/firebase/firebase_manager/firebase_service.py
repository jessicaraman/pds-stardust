import firebase_admin
from firebase_admin import credentials
from firebase_admin import messaging

from configuration import globalconfig as cfg

cred = credentials.Certificate(cfg.firebasekey)
firebase_admin.initialize_app(cred)


def sendnotificationto(token):
  print("token = " + token)

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
  print('Successfully sent message:', response)

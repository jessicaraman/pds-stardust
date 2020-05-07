import firebase_admin
from firebase_admin import messaging
from firebase_admin import credentials
from configuration import globalconfig as cfg


cred = credentials.Certificate(cfg.firebasekey)
firebase_admin.initialize_app(cred)

def sendnotificationto(token):
  print(token)
  #registration_token = 'dtyAPHQcsXDSXLzC_1N_4z:APA91bGJsCXrcW_St3fpw_-GAnfVUIgKSxuXZUF4d47DHbe7eLDvLRLJCtoY6Mday15yyjXxjnag0xptKDib_KBtiYFmubrPdZG2FtjvZ-iIcG4Q7HahZmubpVu2cIB3CmRArNRV5AiU'
  #registration_token = 'dtyAPHQcsXDSXLzC_1N_4z:APA91bGJsCXrcW_St3fpw_-GAnfVUIgKSxuXZUF4d47DHbe7eLDvLRLJCtoY6Mday15yyjXxjnag0xptKDib_KBtiYFmubrPdZG2FtjvZ-iIcG4Q7HahZmubpVu2cIB3CmRArNRV5AiU'

  message = messaging.Message(
    notification=messaging.Notification(
      title='Esipe',
      body='Zi notif',
    ),
    data={
      'subject': 'IT',
      'place': '40',
    },
    token=token,
  )
  response = messaging.send(message)
  print('Successfully sent message:', response)
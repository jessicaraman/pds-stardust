from firebase_admin import messaging
from firebase_admin import credentials
import firebase_admin

cred = credentials.Certificate("firebasePrivateKey/pds-facial-recognition-firebase-adminsdk-s9pux-948e530c8c.json")
firebase_admin.initialize_app(cred)

def sendnotificationto(token):
  print(token)
  registration_token = 'dtyAPHQcsXDSXLzC_1N_4z:APA91bGJsCXrcW_St3fpw_-GAnfVUIgKSxuXZUF4d47DHbe7eLDvLRLJCtoY6Mday15yyjXxjnag0xptKDib_KBtiYFmubrPdZG2FtjvZ-iIcG4Q7HahZmubpVu2cIB3CmRArNRV5AiU'
  message = messaging.Message(
    notification=messaging.Notification(
      title='JENAIMARRRRE',
      body='JENAIMARRRRE',
    ),
    data={
      'subject': 'IT',
      'place': '40',
    },
    token=registration_token,
  )
  response = messaging.send(message)
  print('Successfully sent message:', response)
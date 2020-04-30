from firebase_admin import messaging
from firebase_admin import credentials
import firebase_admin

cred = credentials.Certificate("firebasePrivateKey/pds-facial-recognition-firebase-adminsdk-s9pux-948e530c8c.json")
firebase_admin.initialize_app(cred)

def sendnotificationto(token):
  print(token)

  registration_token = 'egjkDCNbQValexTOKEC5k6:APA91bGU3E0bSmuHR5GvC-a9dRPtAulG1-lbJU9BP-Y-QarLQc24em6Ab7s4oJtXp5jS2OLtnob0hDOqA6epsujm7Ra31TsN6T17lvg8M5eQc4tXEvH6fucFkdailIeEYLnKMlWw1AtD'
  message = messaging.Message(
    notification=messaging.Notification(
      title='Esipe',
      body='New courses!',
    ),
    data={
      'subject': 'IT',
      'place': '40',
    },
    token=registration_token,
  )
  response = messaging.send(message)
  print('Successfully sent message:', response)
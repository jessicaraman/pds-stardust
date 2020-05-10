import unittest
import firebase_admin
from firebase_admin import credentials
from configuration import globalconfig as cfg
from firebase_admin import messaging
from firebase.firebase_manager.firebase_service import FirebaseService

class TestFirebaseMessassing(unittest.TestCase):

    def test_send_notif_data(self):
        f = FirebaseService()
        f.sendnotificationto(cfg.tokentest)
        assert str(f.message.token) == cfg.tokentest
        assert str(f.message.data) == "{'subject': 'IT', 'place': '40'}"
        assert 'projects/pds-facial-recognition/messages/' in str(f.response)

    def test_empty_message(self):
        assert str(messaging.Message(token='abcd')) == '{"token": "abcd"}'
        assert str(messaging.Message(topic='id')) == '{"topic": "id"}'

    def test_messassing(self):
        assert str(messaging.Message(topic='id', data={})) == '{"topic": "id"}'
        assert str(messaging.Message(topic='topic', data={'fname': 'max', 'name': 'ngu'})) == '{"data": {"fname": "max", "name": "ngu"}, "topic": "topic"}'

    if __name__ == '__main__':
        unittest.main()

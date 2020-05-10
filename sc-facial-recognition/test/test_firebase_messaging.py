import unittest

from firebase_admin import messaging

from firebase.firebase_manager.firebase_service import FirebaseService


### TestFirebaseMessaging :
class TestFirebaseMessassing(unittest.TestCase):

    def test_send_notif_data(self):
        token_test = 'dNi4ALm_Qw-rMYVfhHrEez:APA91bEL-7eFpR1aed5uT6I7wU_ReW8T6ofZdb_tNvrJMCqmtjMjdDF5JD3vMjgd3I4r4SdfPQ3YNz6T_K9FBvYss3w0KVZY4Pj5cy7gLBjfq7jYigqkwQgui2OiwcEaNpF1FCYI_Vut'
        f = FirebaseService()
        f.sendnotificationto(token_test)
        assert str(f.message.token) == token_test
        assert str(f.message.data) == "{'subject': 'IT', 'place': '40'}"
        assert 'projects/pds-facial-recognition/messages/' in str(f.response)

    def test_empty_message(self):
        assert str(messaging.Message(token='abcd')) == '{"token": "abcd"}'
        assert str(messaging.Message(topic='id')) == '{"topic": "id"}'

    def test_messassing(self):
        assert str(messaging.Message(topic='id', data={})) == '{"topic": "id"}'
        assert str(messaging.Message(topic='topic', data={'fname': 'max',
                                                          'name': 'ngu'})) == '{"data": {"fname": "max", "name": "ngu"}, "topic": "topic"}'

    if __name__ == '__main__':
        unittest.main()
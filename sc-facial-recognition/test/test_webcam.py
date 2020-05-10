import unittest

from recognition_process.train_model import TrainModel
from recognition_process.webcam import Webcam


class TestWebcam(unittest.TestCase):

    def test_verif_token_in_array(self):
        train_model = TrainModel()
        train_model.train()
        w = Webcam(train_model)
        name = "user"
        arr = [{"name": name, "notified": "true"}]
        self.assertEqual(w.verify_token_notified(name, arr), 'in array')

    def test_verif_token(self):
        token_test = 'dNi4ALm_Qw-rMYVfhHrEez:APA91bEL-7eFpR1aed5uT6I7wU_ReW8T6ofZdb_tNvrJMCqmtjMjdDF5JD3vMjgd3I4r4SdfPQ3YNz6T_K9FBvYss3w0KVZY4Pj5cy7gLBjfq7jYigqkwQgui2OiwcEaNpF1FCYI_Vut'
        train_model = TrainModel()
        train_model.train()
        w = Webcam(train_model)
        name = "pds_tu"
        arr = []
        self.assertEqual(w.verify_token_notified(name, arr), token_test)


if __name__ == '__main__':
    unittest.main()

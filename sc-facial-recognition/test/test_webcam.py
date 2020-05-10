import unittest

from recognition_process.train_model import TrainModel
from recognition_process.webcam import Webcam
import os
from cache_manager.cache import Cache
from configuration import globalconfig as cfg

class TestWebcam(unittest.TestCase):

    def test_verif_token_in_array(self):
        train_model = TrainModel()
        train_model.train()
        w = Webcam(train_model)
        name= "user"
        arr = [{"name": name, "notified": "true"}]
        self.assertEqual(w.verify_token_notified(name,arr), 'in array')

    def test_verif_token(self):
        train_model = TrainModel()
        train_model.train()
        w = Webcam(train_model)
        name= "maxime"
        arr = []
        self.assertEqual(w.verify_token_notified(name,arr), cfg.tokentest)

if __name__ == '__main__':
    unittest.main()

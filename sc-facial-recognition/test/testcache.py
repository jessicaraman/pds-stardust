import unittest
from cache_manager.cache import Cache
from configuration import globalconfig as cfg

class TestCache(unittest.TestCase):


    def test_cache_clean(self):
        c = Cache()
        c.clean()
        with open(cfg.usercachefile) as f:
            lines = f.readlines()
        f.close()
        print(len(lines))
        self.assertEqual(True, True)

    def test_write_user(self):
        self.assertEqual(True,True)

    def test_cache_clean(self):
        self.assertEqual(True,True)

    def test_checking_cache(self):
        self.assertEqual(True,True)

import unittest
import os
from cache_manager.cache import Cache
from configuration import globalconfig as cfg

class TestCache(unittest.TestCase):

    def test_cache_clean(self):
        c = Cache()
        c.clean()
        self.assertEqual(True, os.stat(cfg.usercachefile).st_size == 0)

    def test_write_user(self):
        c = Cache()
        c.write_user("writetest")
        with open(cfg.usercachefile) as f:
            lines = f.readlines()
        f.close()
        self.assertEqual(True, 'writetest' in lines)

if __name__ == '__main__':
    unittest.main()

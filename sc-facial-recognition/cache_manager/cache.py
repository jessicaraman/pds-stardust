from firebase.firebase_manager.firebase_service import FirebaseService
from configuration import globalconfig as cfg

class Cache:
    def __init__(self):
        self.file = open(cfg.usercachefile, "r+")
        self.file.close()

    def clean(self):
        self.file = open(cfg.usercachefile, "r+")
        self.file.truncate(0)
        self.file.close()

    def defaultfill(self):
        self.clean()
        self.file = open(cfg.usercachefile, "a")
        self.file.write("Goldorak")
        self.file.write("\nRahan")
        self.file.write("\nAlbator")
        self.file.write("\nCapitaineFlamme")
        self.file.close()

    def write_user(self,name):
        self.file = open(cfg.usercachefile, "a")
        self.file.write("\n" + name)
        self.file.close()

    def checking_cache(self,name, proba):
        with open(cfg.usercachefile) as f:
            lines = f.readlines()
        f.close()

        if ((name in lines) is False) and ((name + '\n' in lines) is False) and proba > 0.5:
            self.write_user(name)
            f = FirebaseService()
            f.sendnotificationto(name)


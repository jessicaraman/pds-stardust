from firebase.firebase_manager.firebaseService import sendnotificationto
from configuration import globalconfig as cfg


def write_user(name):
    fi = open(cfg.usercachefile, "a")
    fi.write("\n" + name)
    fi.close()


def checking_cache(name, proba):
    with open(cfg.usercachefile) as f:
        lines = f.readlines()
    f.close()

    if ((name in lines) is False) and ((name + '\n' in lines) is False) and proba > 0.5:
        write_user(name)
        sendnotificationto(name)

#USAGE
#python cache_clean.py
from configuration import globalconfig as cfg

def clean():
    file = open(cfg.usercachefile,"r+")
    file.truncate(0)
    file.close()
    f = open(cfg.usercachefile, "r")
    contents = f.read()
    print(contents)
    f.close()
clean()
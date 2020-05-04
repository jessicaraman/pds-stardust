#USAGE
#python cache_fill.py
from cache_manager.cache_clean import *
from configuration import globalconfig as cfg



def fill():
    clean()
    fi = open(cfg.usercachefile, "a")
    fi.write("Goldorak")
    fi.write("\nRahan")
    fi.write("\nAlbator")
    fi.write("\nCapitaineFlamme")
    fi.close()
    f = open(cfg.usercachefile, "r")
    contents = f.read()
    print(contents)
    f.close()

fill()
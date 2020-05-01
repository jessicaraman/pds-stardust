#USAGE
#python cacheFill.py
from cacheClean import *
userFile = "listUsersNotified.txt"



def fill():
    clean()
    fi = open(userFile, "a")
    fi.write("Goldorak")
    fi.write("\nRahan")
    fi.write("\nAlbator")
    fi.write("\nCapitaineFlamme")
    fi.close()
    f = open(userFile, "r")
    contents = f.read()
    print(contents)
    f.close()

fill()
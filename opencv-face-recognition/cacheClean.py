#USAGE
#python cacheClean.py
userFile = "listUsersNotified.txt"

def clean():
    file = open(userFile,"r+")
    file.truncate(0)
    file.close()
    f = open(userFile, "r")
    contents = f.read()
    print(contents)
    f.close()
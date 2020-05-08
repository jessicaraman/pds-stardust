#
usercachefile = "cache_manager/users_notified.txt"

# recognize via smartphone
streamurl = "http://192.168.1.44:8080/shot.jpg"

#
dataset = "dataset"
embedding = "output/embeddings.pickle"
detector = "face_detection_model"
embedding_model = "openface_nn4.small2.v2.t7"
confidence = 0.65

# Modeler & Loader
format_model = "res10_300x300_ssd_iter_140000.caffemodel"
embedder = "openface_nn4.small2.v1.t7"

#
recognizer = "output/recognizer.pickle"
le = "output/le.pickle"


# firebase cloud messaging key
firebasekey = "firebase/firebase_private_key/pds-facial-recognition-firebase-adminsdk-s9pux-948e530c8c.json"

# SSL configuration
ssl_certificate = "ssl/certificate.crt"
ssl_certificate_key = "ssl/keyrsa.key"
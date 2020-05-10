# file user cache
usercachefile = "cache_manager/users_notified.txt"

# recognize via smartphone
streamurl = "http://08e8afbe.ngrok.io/video"

# file and folders used for recognition process
dataset = "dataset"
embedding = "output/embeddings.pickle"
detector = "face_detection_model"
#embedding_model = "openface_nn4.small2.v2.t7"
confidence = 0.65
recognizer = "output/recognizer.pickle"
le = "output/le.pickle"
format_model = "res10_300x300_ssd_iter_140000.caffemodel"

embedder = "openface_nn4.small2.v1.t7"

# firebase cloud messaging key
firebase_key = "firebase/firebase_private_key/pds-facial-recognition-firebase-adminsdk-s9pux-948e530c8c.json"

# request sc-account getToken by username identified
url_sc_account_api = "https://172.31.249.114:9980/api/account/token"
#url_sc_account_api = "https://account:8085/token"

# SSL configuration
ssl_certificate = "ssl/certificate.crt"
ssl_certificate_key = "ssl/certificatekey.key"
ssl_ca_pem = "stardustCA.pem"
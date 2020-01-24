from src.database import db


class User(db.Model):
    __tablename__ = "users"
    id = db.Column(db.String(64), primary_key=True, nullable=False, unique=True)
    pseudo = db.Column(db.String(64), primary_key=True, nullable=False)
    creation_date = db.Column(db.DateTime, nullable=False)
    update_date = db.Column(db.DateTime, nullable=True)
    tweets = db.relationship('Tweet', back_populates="user")

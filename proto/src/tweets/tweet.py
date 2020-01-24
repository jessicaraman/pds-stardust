from src.database import db

from ..users import user


class Tweet(db.Model):
    __tablename__ = "tweets"
    id = db.Column(db.String(64), primary_key=True, nullable=False, unique=True)
    message = db.Column(db.String(280), nullable=False)
    creation_date = db.Column(db.DateTime, nullable=False)
    update_date = db.Column(db.DateTime, nullable=True)
    user_id = db.Column(db.String(64), db.ForeignKey(user.User.id))
    user = db.relationship('User', back_populates="tweets")


import os

postgres_local_base = 'postgres+psycopg2://pds:pds@postgres:5432/pds'

basedir = os.path.abspath(os.path.dirname(__file__))


class Config:
    DEBUG = False


class DevelopmentConfig(Config):
    SQLALCHEMY_DATABASE_URI = postgres_local_base
    DEBUG = True
    SQLALCHEMY_TRACK_MODIFICATIONS = True


class ProductionConfig(Config):
    DEBUG = False
    SQLALCHEMY_DATABASE_URI = postgres_local_base


config_by_name = dict(
    dev=DevelopmentConfig,
    prod=ProductionConfig
)

import os

from flask_migrate import Migrate, MigrateCommand
from flask_script import Manager

from src.app import create_app
from src.database import db

app = create_app(os.getenv('PYPOC_ENV') or 'dev')

app.app_context().push()

manager = Manager(app)

migrate = Migrate(app, db, directory='alembic')

manager.add_command('db', MigrateCommand)


@manager.command
def run():
    app.run(host="0.0.0.0", port=5000)


if __name__ == '__main__':
    manager.run()
    db.create_all()

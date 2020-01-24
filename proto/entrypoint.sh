#!/bin/bash

#python3 manage.py db stamp head
python3 manage.py db migrate
#python3 manage.py db upgrade
python3 manage.py run
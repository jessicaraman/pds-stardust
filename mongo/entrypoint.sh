#!/bin/bash
set -e

mongo <<EOF
use admin
databases = ['geolocation', 'cspath', 'sensor', 'frequentation', 'account']

for (var i = 0; i <= databases.length - 1; i++) {

    db = db.getSiblingDB(databases[i])

    db.createUser({
        user: "$mongoUser",
        pwd: "$mongoPassword",
        roles: ["readWrite"]
    })
}
EOF

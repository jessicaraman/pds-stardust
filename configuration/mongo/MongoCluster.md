# Before Installation

On all nodes :

```shell
export node1=192.168.0.21 && 
export node2=192.168.0.22 && 
export node3=192.168.0.23
```

# Installation

On `node1`, run a mongo container without authentication : 

```shell
docker run \
--name mongo \
-v /mnt/mongo1/data:/data/db \
-v /mnt/mongo1-config:/opt/keyfile \
--hostname="node1" \
-p 27017:27017 -d mongo:4.2.3
```

Enter the mongo container :

```
docker exec -it mongo mongo
```

Switch to `admin` database :

```
use admin
```

Create an admin and root user :

```javascript
db.createUser( {
     user: "xxxx",
     pwd: "xxxx",
     roles: [ { role: "userAdminAnyDatabase", db: "admin" } ]
   });

db.createUser( {
     user: "xxxx",
     pwd: "xxxx",
     roles: [ { role: "root", db: "admin" } ]
   });
```

Delete the container :

```
docker rm mongo --force
```

On `node1`, recreate the mongo container with authentication : 

```shell
docker run \
--name mongo \
--network pds \
-v /mnt/mongo1/data:/data/db \
-v /mnt/mongo1-config:/opt/keyfile \
--hostname="node1" \
--add-host node1:${node1} \
--add-host node2:${node2} \
--add-host node3:${node3} \
-p 27017:27017 -d mongo:4.2.3 \
--keyFile /opt/keyfile/keyfile \
--replSet "rs0"
```

Switch and authenticate to `admin` database :

```
use admin
db.auth("xxx", "xxx")
```

Initiate the replica set :

```
rs.initiate()
```

On `node2`, run a mongo container with authentication : 

```shell
docker run \
--name mongo \
--network pds \
-v /mnt/mongo2/data:/data/db \
-v /mnt/mongo2-config:/opt/keyfile \
--hostname="node2" \
--add-host node1:${node1} \
--add-host node2:${node2} \
--add-host node3:${node3} \
-p 27017:27017 -d mongo:4.2.3 \
--keyFile /opt/keyfile/keyfile \
--replSet "rs0"
```

On `node3`, run a mongo container with authentication : 

```shell
docker run \
--name mongo \
--network pds \
-v /mnt/mongo3/data:/data/db \
-v /mnt/mongo3-config:/opt/keyfile \
--hostname="node3" \
--add-host node1:${node1} \
--add-host node2:${node2} \
--add-host node3:${node3} \
-p 27017:27017 -d mongo:4.2.3 \
--keyFile /opt/keyfile/keyfile \
--replSet "rs0"
```

On `node1`, register the other nodes onto the replica set :

```shell
rs.add("node2")
rs.add("node3")
```


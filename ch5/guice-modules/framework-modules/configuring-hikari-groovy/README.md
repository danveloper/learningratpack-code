Start a MySQL instance on localhost and run this:

```
mysql> create database db;
Query OK, 1 row affected (0.02 sec)

mysql> use db
Database changed
mysql> create table user (username varchar(255) not null, email varchar(255) not null);
Query OK, 0 rows affected (0.07 sec)

mysql> insert into user values ('danveloper', 'danielpwoods@gmail.com');
Query OK, 1 row affected (0.02 sec)

mysql> insert into user values ('ldaley', 'ld@ldaley.com');
Query OK, 1 row affected (0.01 sec)
```

Then run:

```
$ curl localhost:5050/users
[{"username":"danveloper","email":"danielpwoods@gmail.com"},{"username":"ldaley","email":"ld@ldaley.com"}]
$
```

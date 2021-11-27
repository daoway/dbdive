docker run --name=mysql -p 3306:3306 mysql/mysql-server:latest

[Entrypoint] GENERATED ROOT PASSWORD: 63bKd/X@P_t0zsB:J408#DbJ5=K?b6R;
/%zLxqf598Z,,299;f?=Yk1Wxz3o?hCx

docker exec -it mysql mysql -uroot -p

ALTER USER 'root'@'localhost' IDENTIFIED BY 'password';

mysql> CREATE USER 'root1'@'%' IDENTIFIED BY 'PASSWORD';
mysql> GRANT ALL PRIVILEGES ON *.* TO 'root1'@'%' WITH GRANT OPTION;
mysql> FLUSH PRIVILEGES;

then connect workbench

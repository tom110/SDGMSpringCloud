# docker環境部分
## redis
```
 docker run --name redis -d -p 6379:6379 hub.c.163.com/library/redis
```
## rabbitmq
```
docker run -d --hostname rabbit -p 32771:5672 -p 4369:4369 -p 5671:5671 -p 25672:25672 --name rabbit -e RABBITMQ_DEFAULT_USER=user -e RABBITMQ_DEFAULT_PASS=password hub.c.163.com/library/rabbitmq:3-management
```
## mysql
```
docker volume create mysql-data
docker run --name mysql -v mysql-data:/var/lib/mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d hub.c.163.com/library/mysql
```

## maven 运行docker
到模块项目根目录
```
mvn clean package -Dmanven.test.skip=true docker:build
```

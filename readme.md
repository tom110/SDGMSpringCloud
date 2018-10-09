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
如果是运行远程的一个加密docker server，需要如下操作：
1. 设置环境变量`DOCKER_HOST`为`tcp://<server_IP>:2376`，设置`DOCKER_TLS_VERIFY`为`1`
2. 把私钥`ca.pem`、`cert.pem`、`key.pem`、`ca-key.pem`复制到`<userhome>/.docker`目录
3. 运行
```
mvn clean package -Dmanven.test.skip=true docker:build
```
==注意，如果dockerFile里的镜像是在server构建的，所以镜像连接要使用对应server的连接==
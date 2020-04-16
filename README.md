Poc de mensageria usando DLQ com Spring, Docker e RabbitMQ.

Usando:

* RabbitMQ
* JDK 11
* Springboot 2.2.1
* IntelliJ
* Rest
* Gradle
* Docker

Subindo um container docker com RabbitMQ:
```
  docker run -d --name rabbitmq \
 -p 5672:5672 \
 -p 15672:15672 \
 --restart=always \
 --hostname rabbitmq-master \
 rabbitmq:3-management
 ```
 
 
Para acessar o RabbitMQ Dashboard:

URL: http://localhost:15672/#/

<p>Usuário: guest</p>
<p>Senha: guest</p>


Este projeto é um produtor e consumidor de mensagem usando RabbitMQ.
Cria o exchange, queue, binding e DLQ no RabbitMQ.

<p>1) Endpoint</p>
Cria apartir da URL uma entidade e a envia para o RabbitMQ

GET -> URL: http://localhost:8080/publisher?name=NOME&id=ID

Ex: http://localhost:8080/publisher?name=Xablau&id=1



<p>2) Endpoint</p>
Consome as mensagem do RabbitMMQ.

GET -> URL: http://localhost:8080/consumer

Será exibito na pagina a entidade que foi criada e enviada no primeiro endpoint.

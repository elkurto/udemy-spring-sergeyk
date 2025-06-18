# Run nginx locally with self-signed cert and https

### How to - Run nginx locally with self-signed cert and https

1. extract nginx.conf
```bash
   mkdir -p ./etc/nginx/

   # EITHER extract nginx.conf from running container
   % docker cp nginx-test00:/etc/nginx/nginx.conf ./etc/nginx/nginx.conf

   # OR pull :image:"nginx" and extract the file.
   % docker pull nginx
   % docker run --rm --entrypoint=cat nginx /etc/nginx/nginx.conf > /etc/nginx/nginx.conf
```

2. make a private-key.pem and cert.pem without passphrase
   // -- alternatively
   // issue command to create private-key.pem and cert.pem with command
   //  openssl req -new -x509 -newkey rsa:4096 -keyout private-key.pem -out cert.pem -days 366  -subj "/CN=example.com"
   //  (i.e. omit :option:"-nodes",and  supply passphrase , then specify passphrase to nginx via nginx.conf
   //   http {
   //       ssl_password_file /etc/keys/global.pass;   # the password file contains the password
   //    ...
   //   }
```bash
   % mkdir -p etc/ssl
   % cd etc/ssl/ 
   % openssl req -new -x509 -newkey rsa:4096 -keyout private-key.pem -out cert.pem -days 366 -nodes -subj "/CN=example.com"
   Generating a 4096 bit RSA private key
   ...++++
   .........................................++++
   writing new private key to 'private-key.pem'

   % ls *.pem
      cert.pem		private-key.pem
```

3. update the :file:"etc/nginx/nginx.conf"
```
# etc/nginx.nginx.conf

user  nginx;
worker_processes  auto;

error_log  /var/log/nginx/error.log notice;
pid        /run/nginx.pid;

events {
worker_connections  1024;
}

http {
include       /etc/nginx/mime.types;
default_type  application/octet-stream;

    log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';

    access_log  /var/log/nginx/access.log  main;

    sendfile        on;
    #tcp_nopush     on;

    keepalive_timeout  65;
    #gzip  on;

    include /etc/nginx/conf.d/*.conf;

    server {
	root          /etc/nginx/html;    # assign content root
       location      / {
       }
	listen               443 ssl;
        server_name         www.example.com;
        ssl_certificate     /etc/ssl/cert.pem;
        ssl_certificate_key /etc/ssl/private-key.pem;
        ssl_protocols       TLSv1.2 TLSv1.3;
        ssl_ciphers         HIGH:!aNULL:!MD5;
        #...
    }
}
```



#### 4. Create a Dockerfile and an index.html file  

```bash
mkdir -p static/html
vi static/html/index.html
```
```html 

<html>
<body>
<h1>This is my index.html</h1>
<h3>Subtitle</h3>
</body>
</html>
```

```bash
vi Dockerfile
```
```
# Dockerfile
## start with image, nginx
FROM nginx
## copy static content to default root location
COPY static/html  /usr/share/nginx/html
```


Validate :
```
   # your directory  should look like the following tree
   tree nginx-test00
   nginx-test00/
   ├── Dockerfile
   ├── etc
   │   ├── nginx
   │   │   └── nginx.conf
   │   └── ssl
   │       ├── cert.pem
   │       └── private-key.pem
   └── static
       └── html
           └── index.html
```

#### 5. create the docker image locally
```bash
docker build -t nginx-test00:latest .
```
#### 6. create a running nginx container
```bash
docker run --name nginx-test00 -d -p 8443:443 nginx-test00:latest
```

```
   // alternative (to docker run command)
   // a. create  a docker compose.yaml file
   // b. docker compose -f compose.yaml up -d
# compose.yaml
services:
nginx-test00:
build: .
ports:
- "8443:443"
```


7. validate
![20250618.docker-desktop-with-nginx-port-8443.png](./support-image/20250618.docker-desktop-with-nginx-port-8443.png)


https://localhost:8443/index.html

<img src="./support-image/20250618.index-html.png" width="300" height="150" alt="20250618.index-html.png"/>
docker-felix
============

Docker image source project for a preconfigured apache felix installation

============
###Features

Provides:
- openjdk 1.7
- apache felix standard distribution 4.2.1
- apache felix config admin 1.4.0
- apache felix dependency manager 3.1
- apache felix deployment admin 0.9.6
- apache felix file install 3.2.8
- apache felix metatype 1.0.4
- apache felixweb console 4.0 + associated plugins
- ops4j pax logging 1.4
- ops4j pax web jetty bundle 2.1.6 ( jetty 8)
- ops4j pax web extender  2.1.6

Base image is Ubuntu 14:04

============
###Volumes

A volume is mounted at /var/felix

An additional configuration instruct the file install bundle to monitor bundles or configurations dropins at /var/felix/fileinstall

============
###Launch

Launch in detach mode : 
```
docker run -d -p 8080:8080 docker-felix
```

Web console is accessible under port 8080

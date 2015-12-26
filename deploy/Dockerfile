FROM java:8
ENV MONGO_HOST 172.17.42.1
ENV SOLR_HOST "http://172.17.42.1:8983/solr/cyloonindexengine"
# Update aptitude with new repo
RUN apt-get update
RUN apt-get install zip
RUN mkdir /cyloon
WORKDIR /cyloon/
RUN wget http://cyloon.com/osgi1.zip
RUN unzip osgi1.zip
WORKDIR /cyloon/osgi
ADD crawler.sh crawler.sh
RUN chmod +x crawler.sh
#RUN ./run.sh

EXPOSE 8080
CMD ["/cyloon/osgi/crawler.sh"]
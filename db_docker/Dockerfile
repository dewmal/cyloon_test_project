FROM java:8

ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/
# Update aptitude with new repo
RUN apt-get update

# Install software
RUN apt-get install -y zip

RUN mkdir /jsdb
RUN mkdir /jsdb/home
WORKDIR /jsdb

RUN wget http://cyloon.com/jasdb_1.0.zip

RUN unzip jasdb_1.0.zip
ADD run.sh /jsdb/jasdb_1.0/run.sh
RUN chmod +x jasdb_1.0/run.sh


EXPOSE 7050
CMD ["/jsdb/jasdb_1.0/run.sh"]
FROM java:8

# Update aptitude with new repo
RUN apt-get update


RUN mkdir /opt/osgi && cd /opt/osgi
WORKDIR /opt/osgi
# Install felix distribution
RUN curl -O http://archive.apache.org/dist/felix/org.apache.felix.main.distribution-4.2.1.tar.gz && \
tar zxf org.apache.felix.main.distribution-4.2.1.tar.gz && rm org.apache.felix.main.distribution-4.2.1.tar.gz && \
mkdir /opt/osgi/felix-framework-4.2.1/load && \
mkdir -p /var/felix/fileinstall

VOLUME /var/felix

ADD repository.xml /opt/osgi/felix-framework-4.2.1/conf/repository.xml
ADD load /opt/osgi/felix-framework-4.2.1/load
ADD config.properties /opt/osgi/felix-framework-4.2.1/conf/config.properties
ADD bundle /opt/osgi/felix-framework-4.2.1/bundle
ADD launch_felix.sh /opt/osgi/felix-framework-4.2.1/launch_felix.sh
ADD felix_config /opt/osgi/felix-framework-4.2.1/felix_config

WORKDIR /opt/osgi/felix-framework-4.2.1

#VOLUME /var/felix
RUN ./launch_felix.sh < felix_config

EXPOSE 8080
# launch with gogo shell in nointeractive to be able to run in detach mode
CMD ["java", "-Dgosh.args=--nointeractive", "-jar","bin/felix.jar"]
FROM java:8

# Update aptitude with new repo
RUN apt-get update

# Install software
RUN apt-get install -y git
# Make ssh dir
RUN mkdir /cyloon
RUN mkdir /cyloon_crawler_db
WORKDIR /cyloon
ADD . /cyloon1
RUN git clone https://github.com/dewmal/cyloon_test_project.git

WORKDIR /cyloon/cyloon_test_project

RUN chmod +x gradlew

RUN ./gradlew clean createOsgiRuntime --info --stacktrace
WORKDIR /cyloon/cyloon_test_project/build/osgi

RUN chmod +x run.sh
RUN ./run.sh

EXPOSE 8080

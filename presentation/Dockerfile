FROM ubuntu:14.04
RUN apt-get update; apt-get dist-upgrade -y; apt-get install -y nodejs nodejs-legacy npm
# COPY index.html /src/
# COPY images/ /src/
# COPY css/ /src/
# COPY js /src/
# COPY bower.json /src
EXPOSE 8000
WORKDIR /src
ADD . /src
RUN cd /src && npm install && npm install -g bower grunt-cli && bower --allow-root install 
CMD ["grunt", "serve"]
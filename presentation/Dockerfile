FROM ubuntu:14.04
CMD ["grunt", "serve"]
# COPY index.html /src/
# COPY images/ /src/
# COPY css/ /src/
# COPY js /src/
# COPY bower.json /src
EXPOSE 8000
WORKDIR /src
RUN apt-get update; apt-get dist-upgrade -y; apt-get install -y nodejs nodejs-legacy npm
RUN npm install -g bower grunt-cli
ADD . /src
RUN cd /src && npm install && bower --allow-root install 
FROM dockerfile/java:oracle-java8
VOLUME /tmp
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle
EXPOSE 31080
ADD /sqrt-server-1.0-SNAPSHOT.jar /sqrt-server.jar
RUN /bin/bash -c 'touch /sqrt-server.jar'
ENTRYPOINT ["/usr/bin/java", "-jar", "/sqrt-server.jar", "--spring.profiles.active=prod"]

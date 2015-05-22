FROM dockerfile/java:oracle-java8
VOLUME /tmp
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle
EXPOSE 31080
ADD /sqrt-client-1.0-SNAPSHOT.jar /sqrt-client.jar
RUN /bin/bash -c 'touch /sqrt-client.jar'
ENTRYPOINT ["/usr/bin/java", "-jar", "/sqrt-client.jar"]

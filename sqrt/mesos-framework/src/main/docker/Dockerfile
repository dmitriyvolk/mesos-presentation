FROM dockerfile/java:oracle-java8
VOLUME /tmp
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle
EXPOSE 31080
ADD /mesos-framework-1.0-SNAPSHOT.jar /mesos-framework.jar
RUN /bin/bash -c 'touch /mesos-framework.jar'
ENTRYPOINT ["/usr/bin/java", "-jar", "/mesos-framework.jar"]

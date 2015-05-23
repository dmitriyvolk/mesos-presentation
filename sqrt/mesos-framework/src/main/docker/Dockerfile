FROM mesosphere/mesos:0.22.0-1.0.ubuntu1404
VOLUME /tmp
ENV JAVA_HOME /usr/lib/jvm/java-7-openjdk-amd64
EXPOSE 31080
ENTRYPOINT ["/usr/bin/java", "-jar", "/mesos-framework.jar", "--spring.profiles.active=prod"]
ADD /mesos-framework-1.0-SNAPSHOT.jar /mesos-framework.jar
RUN /bin/bash -c 'touch /mesos-framework.jar'

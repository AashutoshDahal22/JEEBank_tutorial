FROM ubuntu:22.04

ENV DEBIAN_FRONTEND=noninteractive
ENV WILDFLY_VERSION=32.0.0.Final
ENV WILDFLY_HOME=/opt/wildfly
ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
ENV PATH=$JAVA_HOME/bin:$WILDFLY_HOME/bin:$PATH
ENV PG_VERSION=16
ENV DATA_DIR=/var/lib/postgresql/data

# Install dependencies
RUN apt-get update && apt-get install -y \
    openjdk-21-jdk \
    wget \
    unzip \
    postgresql \
    postgresql-contrib \
    supervisor \
    gosu \
 && rm -rf /var/lib/apt/lists/*

# Install WildFly
RUN wget https://github.com/wildfly/wildfly/releases/download/${WILDFLY_VERSION}/wildfly-${WILDFLY_VERSION}.zip \
 && unzip wildfly-${WILDFLY_VERSION}.zip \
 && mv wildfly-${WILDFLY_VERSION} ${WILDFLY_HOME} \
 && rm wildfly-${WILDFLY_VERSION}.zip

# Create users and directories
RUN useradd -r wildfly \
 && mkdir -p $DATA_DIR \
 && chown -R postgres:postgres $DATA_DIR \
 && chown -R wildfly:wildfly $WILDFLY_HOME

RUN mkdir -p \
    ${WILDFLY_HOME}/modules/system/layers/base/org/postgresql/main

COPY modules/org/postgresql/main/postgresql-42.6.0.jar \
     ${WILDFLY_HOME}/modules/system/layers/base/org/postgresql/main/

COPY modules/org/postgresql/main/module.xml \
     ${WILDFLY_HOME}/modules/system/layers/base/org/postgresql/main/

COPY configure-ds.cli ${WILDFLY_HOME}/

COPY web/target/web-1.0-SNAPSHOT.war \
     ${WILDFLY_HOME}/standalone/deployments/

RUN chown -R wildfly:wildfly ${WILDFLY_HOME}

USER wildfly

EXPOSE 8080 9990

CMD ["/bin/bash", "-c", \
     "/opt/wildfly/bin/jboss-cli.sh --file=/opt/wildfly/configure-ds.cli && \
      /opt/wildfly/bin/standalone.sh -b 0.0.0.0"]

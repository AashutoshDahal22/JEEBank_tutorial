FROM ubuntu:22.04

ENV DEBIAN_FRONTEND=noninteractive
ENV WILDFLY_VERSION=32.0.0.Final
ENV WILDFLY_HOME=/opt/wildfly
ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
ENV PATH=$JAVA_HOME/bin:$WILDFLY_HOME/bin:$PATH
ENV PG_VERSION=16
ENV DATA_DIR=/var/lib/postgresql/data
ENV NEW_DB=mydb
ENV NEW_USER=myuser
ENV NEW_PASS=mypassword
ENV PG_SUPERPASS=postgrespassword


# Install dependencies
RUN apt-get update && apt-get install -y wget gnupg lsb-release ca-certificates \
 && echo "deb http://apt.postgresql.org/pub/repos/apt $(lsb_release -cs)-pgdg main" > /etc/apt/sources.list.d/pgdg.list \
 && wget --quiet -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc | apt-key add - \
 && apt-get update \
 && apt-get install -y postgresql-16 postgresql-client-16 postgresql-contrib supervisor gosu openjdk-21-jdk unzip python3-pip \
 && pip3 install psycopg2-binary \
 && rm -rf /var/lib/apt/lists/*


# Install WildFly
RUN wget https://github.com/wildfly/wildfly/releases/download/${WILDFLY_VERSION}/wildfly-${WILDFLY_VERSION}.zip \
 && unzip wildfly-${WILDFLY_VERSION}.zip \
 && mv wildfly-${WILDFLY_VERSION} ${WILDFLY_HOME} \
 && rm wildfly-${WILDFLY_VERSION}.zip

# Create directories and set permissions
RUN useradd -r wildfly \
 && mkdir -p $DATA_DIR \
 && chown -R postgres:postgres $DATA_DIR \
 && chown -R wildfly:wildfly $WILDFLY_HOME

# Initialize Postgres data directory
USER postgres
RUN /usr/lib/postgresql/$PG_VERSION/bin/initdb -D $DATA_DIR
USER root

# Add PostgreSQL driver to WildFly
RUN mkdir -p ${WILDFLY_HOME}/modules/system/layers/base/org/postgresql/main
COPY modules/org/postgresql/main/postgresql-42.6.0.jar \
     ${WILDFLY_HOME}/modules/system/layers/base/org/postgresql/main/
COPY modules/org/postgresql/main/module.xml \
     ${WILDFLY_HOME}/modules/system/layers/base/org/postgresql/main/

# Copy WAR and CLI config
COPY web/target/web-1.0-SNAPSHOT.war ${WILDFLY_HOME}/standalone/deployments/
COPY configure-ds.cli ${WILDFLY_HOME}/

# Change ownership to wildfly
RUN chown -R wildfly:wildfly ${WILDFLY_HOME}

RUN mkdir -p /opt/wildfly/standalone/log \
    && chown -R wildfly:wildfly /opt/wildfly/standalone/log

# Copy Supervisor config
COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf

COPY init_db.sh /opt/init_db.sh
RUN chmod +x /opt/init_db.sh

EXPOSE 8080 9990 5432

# Use Supervisor to start Postgres and WildFly
CMD ["/usr/bin/supervisord", "-n"]

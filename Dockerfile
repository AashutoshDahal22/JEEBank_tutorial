FROM quay.io/wildfly/wildfly:latest

# creating the modules directory for wildfly
RUN mkdir -p /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main

# copying the modules.xml and postgres sql
COPY modules/org/postgresql/main/postgresql-42.6.0.jar /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main/
COPY modules/org/postgresql/main/module.xml /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main/

# runnign the cli script for pre configurations of wildfly server
COPY configure-ds.cli /opt/jboss/wildfly/configure-ds.cli

# deploying the actual war
COPY /web/target/web-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/

# running the cli in the embedded mode
RUN /opt/jboss/wildfly/bin/jboss-cli.sh --file=/opt/jboss/wildfly/configure-ds.cli


CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]

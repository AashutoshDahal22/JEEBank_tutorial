FROM quay.io/wildfly/wildfly:latest

# Create module directory
RUN mkdir -p /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main

# Copy JAR and module.xml
COPY modules/org/postgresql/main/postgresql-42.6.0.jar /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main/
COPY modules/org/postgresql/main/module.xml /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main/

# Copy CLI script
COPY configure-ds.cli /opt/jboss/wildfly/configure-ds.cli

# Deploy your application WAR
COPY /web/target/web-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/

# Use CLI in embedded server mode
RUN /opt/jboss/wildfly/bin/jboss-cli.sh --file=/opt/jboss/wildfly/configure-ds.cli

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]

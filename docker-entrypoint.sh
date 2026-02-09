#!/bin/bash
set -e

DATA_DIR=/var/lib/postgresql/data
PG_BIN=/usr/lib/postgresql/14/bin

# Ensure data directory exists
mkdir -p $DATA_DIR
chown -R postgres:postgres $DATA_DIR

# Initialize PostgreSQL if not already initialized
if [ ! -s "$DATA_DIR/PG_VERSION" ]; then
    echo "Initializing PostgreSQL database..."
    gosu postgres $PG_BIN/initdb -D $DATA_DIR
    gosu postgres $PG_BIN/pg_ctl -D $DATA_DIR -o "-c listen_addresses='*'" -w start
    gosu postgres $PG_BIN/psql -c "CREATE USER myuser WITH PASSWORD 'mypassword';"
    gosu postgres $PG_BIN/psql -c "CREATE DATABASE mydb OWNER myuser;"
    gosu postgres $PG_BIN/pg_ctl -D $DATA_DIR stop
fi

# Start supervisord (runs Postgres + WildFly)
exec /usr/bin/supervisord -c /etc/supervisord.conf

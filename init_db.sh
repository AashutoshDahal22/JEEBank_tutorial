#!/bin/bash
set -e

# Wait until Postgres is ready
until pg_isready -h localhost -p 5432 -U postgres; do
  echo "Waiting for Postgres..."
  sleep 2
done

# Create user and database
psql -v ON_ERROR_STOP=1 --username "postgres" <<-EOSQL
    CREATE USER myuser WITH PASSWORD 'mypassword';
    CREATE DATABASE mydb OWNER myuser;
    GRANT ALL PRIVILEGES ON DATABASE mydb TO myuser;
EOSQL

echo "Database and user created successfully."

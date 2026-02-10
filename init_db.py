import time
import psycopg2
from psycopg2 import sql

# wait until Postgres is ready
while True:
    try:
        conn = psycopg2.connect(dbname="postgres", user="postgres", password="yourpassword", host="localhost")
        conn.close()
        break
    except:
        print("Waiting for Postgres...")
        time.sleep(2)

# Now create user and DB
conn = psycopg2.connect(dbname="postgres", user="postgres", password="yourpassword", host="localhost")
conn.autocommit = True
cur = conn.cursor()

cur.execute("CREATE USER myuser WITH PASSWORD 'mypassword';")
cur.execute("CREATE DATABASE mydb OWNER myuser;")
cur.execute("GRANT ALL PRIVILEGES ON DATABASE mydb TO myuser;")

cur.close()
conn.close()
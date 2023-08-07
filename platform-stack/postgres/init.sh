#!/bin/bash
set -e
set -u

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
  DO
  \$do\$
  BEGIN
    IF NOT EXISTS (
      SELECT FROM pg_catalog.pg_roles
      WHERE rolname = 'root') THEN

      CREATE USER root;
      ALTER USER root WITH SUPERUSER;
    END IF;
  END
  \$do\$;
EOSQL

function create_user_and_database() {
	local database=$1
	echo "  Creating user and database '$database' with $POSTGRES_USER"
	psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
		SELECT 'CREATE DATABASE "$database"' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = '$database')\gexec
		DO
		\$do\$
		BEGIN
			IF NOT EXISTS (
				SELECT 
				FROM   pg_catalog.pg_roles 
				WHERE  rolname = '$POSTGRES_USER') THEN
				
				CREATE ROLE "$POSTGRES_USER" LOGIN PASSWORD '$POSTGRES_PASSWORD';
			END IF;
		END
		\$do\$;
		GRANT ALL PRIVILEGES ON DATABASE "$database" TO "$POSTGRES_USER";
	EOSQL
}

if [ -n "$POSTGRES_MULTIPLE_DATABASES" ]; then
	echo "Multiple database creation requested: $POSTGRES_MULTIPLE_DATABASES"
	for db in $(echo "$POSTGRES_MULTIPLE_DATABASES" | tr ',' ' '); do
		create_user_and_database "$db"
	done
	echo "Multiple databases created"
fi


#!/bin/bash

echo "preparing database..."

set -e
set -u

function create_user_and_database() {
	local database=$1
	echo "  Creating user and database '$database'"

	# Check if the user already exists
	local user_exists=$(psql -tAc "SELECT 1 FROM pg_roles WHERE rolname='$database'")
	if [[ $user_exists -eq 1 ]]; then
	    echo "  User '$database' already exists."
	else
	    psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" << EOSQL
	        CREATE USER $database;
EOSQL
	fi

	# Check if the database already exists
	local db_exists=$(psql -tAc "SELECT 1 FROM pg_database WHERE datname='$database'")
	if [[ $db_exists -eq 1 ]]; then
	    echo "  Database '$database' already exists."
	else
	    psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" << EOSQL
	        CREATE DATABASE $database;
EOSQL
	fi

	# grant rights to the database
	psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" << EOSQL
	    GRANT ALL PRIVILEGES ON DATABASE $database TO $database;
EOSQL
}


if [ -n "$POSTGRES_MULTIPLE_DATABASES" ]; then
	echo "Multiple database creation requested: $POSTGRES_MULTIPLE_DATABASES"
	for db in $(echo "$POSTGRES_MULTIPLE_DATABASES" | tr "," " "); do
		create_user_and_database "$db"
	done
	echo "Multiple databases created"
fi

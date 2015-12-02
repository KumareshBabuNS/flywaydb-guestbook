#!/bin/bash

echo "------------- Copy FlywayDB schema creation script to FlywayDB folder tree -------------"
cp ../flywaydb/V2__create-table.sql $FWDB_HOME/sql/

echo "------------- Run FlywayDB -------------"
$FWDB_HOME/flyway migrate

echo "------------- Push Blue -------------"
cd ../spring-boot-guestbook
cf push

echo "------------- Add PROD route to Blue -------------"
cf map-route guestbook-fwdb-blue cfapps.io -n guestbook-fwdb

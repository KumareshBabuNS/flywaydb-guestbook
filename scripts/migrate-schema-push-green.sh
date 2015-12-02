#!/bin/bash

echo "------------- Copy FlywayDB new column script to FlywayDB folder tree -------------"
cp ../flywaydb/V3__new-column.sql $FWDB_HOME/sql/

echo "------------- Run FlywayDB -------------"
$FWDB_HOME/flyway migrate

echo "------------- Push Green -------------"
cd ../spring-boot-guestbook-v2
cf push
echo "------------- Switch PROD route to Green -------------"
cf map-route guestbook-fwdb-green cfapps.io -n guestbook-fwdb
cf unmap-route guestbook-fwdb-blue cfapps.io -n guestbook-fwdb

echo "------------- Copy FlywayDB schema creation script to FlywayDB folder tree -------------"
cp ../flywaydb/V4__not-null-constraint.sql $FWDB_HOME/sql/

echo "------------- Run FlywayDB -------------"
$FWDB_HOME/flyway migrate

#!/bin/bash

echo "------------- Copy FlywayDB remove column script to FlywayDB folder tree -------------"
cp ../flywaydb/V5__remove-column.sql $FWDB_HOME/sql/

echo "------------- Run FlywayDB -------------"
$FWDB_HOME/flyway migrate

echo "------------- Switch PROD route to Blue -------------"
cf map-route guestbook-fwdb-blue cfapps.io -n guestbook-fwdb
cf unmap-route guestbook-fwdb-green cfapps.io -n guestbook-fwdb

#!/bin/bash
cd ~/AFE\ Demo/corpinfo

echo "Pushing application"
cf push
echo "Done pushing application"

echo "Performing Schema Changes"
cd ~/Downloads/flyway-3.2.1
./flyway migrate
echo "Schema changes done- Added Column Ticker"

echo "Pushing application V2"
cd ~/AFE\ Demo/corpinfo-v2
cf push
echo "Done pushing application V2"

echo "Performing Another Schema Change"
cd ~/Downloads/flyway-3.2.1
cp scripts/V3__not-null-constraint.sql sql/
cp scripts/V4__add-ticker-value.sql sql/
./flyway migrate
echo "Schema changes done- Added Not null Constraint"

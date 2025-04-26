#!/bin/bash

set -eu

# Load the end-user configuration
# source /app/data/env.sh

chown -R cloudron:cloudron /app/data

echo "==> Starting application"
# exec gosu cloudron:cloudron java ${JAVA_OPTS} -jar /app/code/app.jar
exec gosu cloudron:cloudron java -jar /app/code/app.jar --debug

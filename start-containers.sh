#!/bin/sh

# Start the other containers within the same network
docker-compose -f docker-compose.yml up -d

# Additional tasks if needed
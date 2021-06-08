#!/bin/sh

docker stop rhsso
docker rm rhsso

docker run --name rhsso \
-e SSO_ADMIN_PASSWORD=admin \
-e SSO_ADMIN_USERNAME=admin \
-p 8080:8080 \
--net rhsso-network \
rhsso-server

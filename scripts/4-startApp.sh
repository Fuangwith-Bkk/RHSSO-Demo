#!/bin/sh

docker stop rhsso-js-console
docker rm rhsso-js-console

docker run --name rhsso-js-console \
-p 8000:8080 rhsso-js-console


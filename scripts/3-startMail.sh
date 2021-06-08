#!/bin/sh

docker stop rhsso-mail
docker rm rhsso-mail

docker run -d -p 8025:8025 \
--name rhsso-mail \
--net rhsso-network mailhog/mailhog

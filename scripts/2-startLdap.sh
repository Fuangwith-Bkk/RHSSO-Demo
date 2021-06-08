#!/bin/sh

docker stop rhsso-ldap
docker rm rhsso-ldap


docker run \
	--env LDAP_ORGANISATION="RHSSO Example Inc." \
	--env LDAP_DOMAIN="example.com" \
    --net rhsso-network \
	--name rhsso-ldap -p 389:389 rhsso-ldap

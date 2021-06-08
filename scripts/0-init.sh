#!/bin/sh

docker network create rhsso-network


mvn clean install
docker build -t rhsso-server -f keycloak/Dockerfile .

docker build -t rhsso-ldap ldap

docker build -t rhsso-js-console js-console


Thank you 
- [Stian Thorgersen](https://github.com/stianst/keycloak-containers-demo) for developing the very great Keycloak demo.
- [K.Chatchai Kongmanee](https://github.com/chatapazar/keycloak-containers-demo) for changing to use RHSSO image.


## Start containers

### Create a user defined network

To make it easy to connect RHSSO to LDAP and the mail server create a user defined network:

    docker network create rhsso-network

### Start RHSSO

We're going to use an extended RHSSO image that includes a custom theme and some custom providers.

First, build the custom providers and themes with:

    mvn clean install

Then build the image based on RHSSO image with:
    
    docker build -t rhsso-server -f keycloak/Dockerfile .

Finally run it with:

    docker run --name rhsso \
    -e SSO_ADMIN_PASSWORD=admin \
    -e SSO_ADMIN_USERNAME=admin \
    -p 8080:8080 \
    --net rhsso-network \
    rhsso-server

### Start LDAP server

For the LDAP part of the demo we need an LDAP server running.

First build the image with:

    docker build -t rhsso-ldap ldap
    
Then run it with:

    docker run \
	--env LDAP_ORGANISATION="RHSSO Example Inc." \
	--env LDAP_DOMAIN="example.com" \
    --net rhsso-network \
	--name rhsso-ldap -p 389:389 rhsso-ldap
    
### Start mail server

In order to allow Keycloak to send emails we need to configure an SMTP server. MailHog provides an excellent email
server that can used for testing.

Start the mail server with:

    docker run -d -p 8025:8025 \
    --name rhsso-mail \
    --net rhsso-network mailhog/mailhog
    
### Start JS Console application

The JS Console application provides a playground to play with tokens issued by Keycloak.

First build the image with:

    docker build -t rhsso-js-console js-console
    
Then run it with:

    docker run --name rhsso-js-console \
    -p 8000:8080 rhsso-js-console


## Or start all containers by using scripts


    ./scripts/0-init.sh
    ./scripts/1-startRHSSO.sh
    ./scripts/2-startLdap.sh
    ./scripts/3-startMail.sh
    ./scripts/4-startApp.sh

[Demo steps](DEMO.md)
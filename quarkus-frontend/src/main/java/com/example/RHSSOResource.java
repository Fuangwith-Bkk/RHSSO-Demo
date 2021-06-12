package com.example;

import java.util.Base64;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import io.quarkus.oidc.IdToken;
import io.quarkus.oidc.AccessTokenCredential;
import org.eclipse.microprofile.jwt.JsonWebToken;


@Path("/")
public class RHSSOResource {
    
    @Inject 
    Template index;

    @IdToken
    JsonWebToken idToken;

    @Inject
    AccessTokenCredential accessToken;


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public TemplateInstance getHome() {
        return getIdToken();
    }

    @GET
    @Path("/accessToken")
    @Produces(MediaType.TEXT_PLAIN)
    public TemplateInstance getAccessToken() {
        return tokenToTemplate(accessToken.getToken())
                .data("accessTokenTab","class=active");
    }

    @GET
    @Path("/idToken")
    @Produces(MediaType.TEXT_PLAIN)
    public TemplateInstance getIdToken() {
        return tokenToTemplate(idToken.getRawToken())
               .data("idTokenTab","class=active");
    }

    private TemplateInstance tokenToTemplate(String token){
        String[] jwt = token.split("\\.");
        String jwtHeader = new String(Base64.getDecoder().decode(jwt[0]));
        String jwtPayload = new String(Base64.getDecoder().decode(jwt[1]));
        
        return index.data("name", idToken.getClaim("name"))
                    .data("token", idToken.getRawToken())
                    .data("header",toPrettyJson(jwtHeader))
                     .data("payload",toPrettyJson(jwtPayload));
    }

    private String toPrettyJson(String jsonString){
        try{
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(jsonString).toPrettyString();
        }catch(Exception ex){
            return jsonString;
        }
    }




}

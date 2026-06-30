package com.example.application.domain1.idp;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/domain1/idp")
public class IdpResource {
    private static final Logger logger = Logger.getLogger(IdpResource.class.getName());
    private static final String LOG_PREFIX = ">>> [IDP]: " + IdpResource.class.getSimpleName() + ": ";

    /**
     * トークンエンドポイント
     */
    @Path("/token")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response exchangeToken(@HeaderParam("Authorization") String authorization, @FormParam("code") String code) {
        logger.info(LOG_PREFIX
                + String.format("post start. Parameters -> authorization=%s, code=%s", authorization, code));

        Map<String, Object> body = new HashMap<>();
        body.put("token", "proto-token-123");
        return Response.ok(body).build();
    }

    /**
     * UserInfo エンドポイント
     */
    @Path("/userInfo")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response userInfo() {
        Map<String, Object> body = new HashMap<>();
        body.put("userId", "1234567");
        body.put("userName", "proto-admin");
        return Response.ok(body).build();
    }
}

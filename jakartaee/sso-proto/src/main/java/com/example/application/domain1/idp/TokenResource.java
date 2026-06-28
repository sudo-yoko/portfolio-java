package com.example.application.domain1.idp;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * トークンエンドポイント
 */
@Path("/domain1/idp/token")
public class TokenResource {
    private static final Logger logger = Logger.getLogger(TokenResource.class.getName());
    private static final String LOG_PREFIX = ">>> [IDP]: " + TokenResource.class.getSimpleName() + ": ";

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(@HeaderParam("Authorization") String authorization, @FormParam("code") String code) {
        logger.info(LOG_PREFIX
                + String.format("post start. Parameters -> authorization=%s, code=%s", authorization, code));

        Map<String, Object> body = new HashMap<>();
        body.put("token", "proto-token-123");
        return Response.ok(body).build();
    }

}

package com.example.application.domain1.idp.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/**
 * トークンエンドポイント
 */
@Path("/token")
public class TokenResource {

    @GET
    public Response getToken() {
        return Response.noContent().build();
    }

}

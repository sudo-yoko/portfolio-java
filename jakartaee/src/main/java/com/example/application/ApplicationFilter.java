package com.example.application;

import java.io.IOException;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.USER)
public class ApplicationFilter implements ContainerRequestFilter {

    @Inject
    private RequestContext context;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        setErrorFormat(requestContext);
    }

    /**
     * エラーレスポンスの形式をセットする
     */
    private void setErrorFormat(ContainerRequestContext requestContext) {
        String format = requestContext.getHeaderString("X-Error-Format");
        context.setErrorFormat(format);
    }
}

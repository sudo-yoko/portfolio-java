package com.example.application;

import java.io.IOException;
import java.util.logging.Logger;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class ExceptionFilter extends HttpFilter {
    private static final Logger logger = Logger.getLogger(ExceptionFilter.class.getName());
    private static final String LOG_PREFIX = ">>> " + ExceptionFilter.class.getSimpleName() + ": ";

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub
        logger.info(LOG_PREFIX + "doFilter");
        super.doFilter(req, res, chain);
    }

}

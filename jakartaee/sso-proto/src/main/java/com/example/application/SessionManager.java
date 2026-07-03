package com.example.application;

import java.util.logging.Logger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionManager {
    private static final Logger logger = Logger.getLogger(SessionManager.class.getName());
    private static final String LOG_PREFIX = ">>> " + SessionManager.class.getSimpleName() + ": ";

    private SessionManager() {

    }

    public static <T> void create(HttpServletRequest req, String key, T value) {
        HttpSession httpSession = req.getSession(true);
        Object session = httpSession.getAttribute(key);
        if (session == null) {
            httpSession.setAttribute(key, value);
            logger.info(LOG_PREFIX + "session created.");
        }
    }

    public static void remove(HttpServletRequest req, String key) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.removeAttribute(key);
            logger.info(LOG_PREFIX + "session removed.");
        }
    }

    public static boolean exists(HttpServletRequest req, String key) {
        HttpSession httpSession = req.getSession(false);
        if (httpSession != null && httpSession.getAttribute(key) != null) {
            logger.info(LOG_PREFIX + "session exists.");
            return true;
        }
        logger.info(LOG_PREFIX + "session does not exist.");
        return false;
    }

    public static <T> T get(HttpServletRequest req, String key, Class<T> type) {
        HttpSession httpSession = req.getSession(false);
        if (httpSession == null) {
            throw new IllegalStateException("session does not exist.");
        }
        Object value = httpSession.getAttribute(key);
        if (value == null) {
            throw new IllegalStateException("session does not exist.");
        }
        if (!type.isInstance(value)) {
            throw new IllegalStateException("session type mismatch key=" + key);
        }
        return type.cast(value);
    }

    public static <T> void set(HttpServletRequest req, String key, T value) {
        HttpSession httpSession = req.getSession(false);
        if (httpSession == null) {
            throw new IllegalStateException("session does not exist.");
        }
        httpSession.setAttribute(key, value);
    }
}

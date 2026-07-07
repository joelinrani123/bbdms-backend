package com.bloodbank.bbdms.security;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Very small in-memory token store used for admin session authentication.
 * A token is issued on login and must be sent as "Authorization: Bearer <token>"
 * on every /api/admin/** request. Tokens expire after 8 hours of issuance.
 */
@Component
public class TokenStore {

    private static final long EXPIRY_MILLIS = 8 * 60 * 60 * 1000L; // 8 hours

    private record Session(String username, long expiresAt) {}

    private final Map<String, Session> tokens = new ConcurrentHashMap<>();

    public String issueToken(String username) {
        String token = UUID.randomUUID().toString().replace("-", "")
                + UUID.randomUUID().toString().replace("-", "");
        tokens.put(token, new Session(username, Instant.now().toEpochMilli() + EXPIRY_MILLIS));
        return token;
    }

    public boolean isValid(String token) {
        if (token == null) return false;
        Session session = tokens.get(token);
        if (session == null) return false;
        if (session.expiresAt() < Instant.now().toEpochMilli()) {
            tokens.remove(token);
            return false;
        }
        return true;
    }

    public String usernameFor(String token) {
        Session session = tokens.get(token);
        return session != null ? session.username() : null;
    }

    public void invalidate(String token) {
        tokens.remove(token);
    }
}

package com.emc.belustyle.security;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenGenerator {
    private static final SecureRandom random = new SecureRandom();
    private static final Base64.Encoder encoder = Base64.getUrlEncoder();

    public static String generateToken() {
        byte[] randomBytes = new byte[24];
        random.nextBytes(randomBytes);
        return encoder.encodeToString(randomBytes);
    }
}

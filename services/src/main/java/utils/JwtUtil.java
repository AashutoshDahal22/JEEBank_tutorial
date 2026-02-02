package utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtUtil {

    private static final String secret = "superSecretKey";
    private static final Long tokenExpirationTime = 15 * 60 * 1000L;

    public static String generateAccessToken(String email, String role) {
        return JWT.create()
                .withIssuer("JEEBank")
                .withSubject(email)
                .withClaim("role", role)
                .withExpiresAt(new Date(System.currentTimeMillis() + tokenExpirationTime))
                .sign(Algorithm.HMAC256(secret));
    }

    public static String generateRefreshToken(String email) {
        return JWT.create()
                .withIssuer("JEEBank")
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 7 days
                .sign(Algorithm.HMAC256(secret));
    }

    public static DecodedJWT validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("JEEBank")
                .build();
        return verifier.verify(token);
    }

    public static String extractRole(String token) {
        try {
            DecodedJWT decodedJWT = validateToken(token);
            return decodedJWT.getClaim("role").asString();
        } catch (Exception e) {
            return null;
        }
    }

    public static String extractUsername(String token) {
        try {
            DecodedJWT decodedJWT = validateToken(token);
            return decodedJWT.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    public static long getAccessTokenExpiration() {
        return tokenExpirationTime;
    }
}
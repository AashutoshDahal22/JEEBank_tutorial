package utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class JwtUtil {

    private static final String secret;
    private static final Long tokenExpirationTime;


    static {
        Properties properties = new Properties();
        try (InputStream input = JwtUtil.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                System.err.println("config.properties not found, using defaults!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        secret = properties.getProperty("jwt.secret", "defaultSecret");
        tokenExpirationTime = Long.parseLong(properties.getProperty("jwt.token.expiration", "900000"));
    }

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

    public static long getAccessTokenExpiration() {
        return tokenExpirationTime;
    }
}
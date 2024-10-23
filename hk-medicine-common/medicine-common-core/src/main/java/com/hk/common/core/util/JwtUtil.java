package com.hk.common.core.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author pengzhengfa
 */
public class JwtUtil {

    private static final String secret = "hk";
    private static final long refreshTokenExpireTime = 14 * 24 * 60 * 60 * 1000;

    public static String createToken(Long userId, Long expireTime) {
        return createTokenWithClaims(userId, expireTime);
    }

    public static String refreshToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT jwt = JWT.require(algorithm).build().verify(token);
            Date expirationDate = jwt.getExpiresAt();
            long currentTime = TimeUtils.cTime().getTime();
            if (expirationDate.getTime() > currentTime) {
                Date newExpirationDate = new Date(expirationDate.getTime() + refreshTokenExpireTime);
                Algorithm newAlgorithm = Algorithm.HMAC256(secret);
                String newToken = JWT.create()
                        .withClaim("userId", jwt.getClaim("userId").asLong())
                        .withExpiresAt(newExpirationDate)
                        .sign(newAlgorithm);
                return newToken;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String createTokenWithClaims(Long userId, Long expireTime) {
        try {
            Date date = new Date(TimeUtils.cTime().getTime() + expireTime);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withClaim("userId", userId)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static boolean verify(String token, Long userId) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("userId", userId).build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isTokenExpired(String token) {
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
            Date expirationDate = jwt.getExpiresAt();
            long currentTime = TimeUtils.cTime().getTime();
            return expirationDate.getTime() < currentTime;
        } catch (Exception e) {
            return true;
        }
    }

    public static Long getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asLong();
        } catch (JWTDecodeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
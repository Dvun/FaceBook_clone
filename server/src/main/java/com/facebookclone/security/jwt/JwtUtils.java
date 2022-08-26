package com.facebookclone.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.facebookclone.exception.BadRequestException;
import com.facebookclone.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${spring.security.jwt.secret}")
    private String jwtSecret;


    public String generateToken(Authentication auth) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());
        UserDetailsImpl principals = (UserDetailsImpl) auth.getPrincipal();
        return JWT.create()
                .withSubject("user details")
                .withClaim("id", principals.getId())
                .withClaim("username", principals.getUsername())
                .withIssuedAt(new Date())
                .withIssuer("Facebook-clone")
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    public String validateToken(String token) throws JWTVerificationException {
        JWTVerifier jwt = JWT.require(Algorithm.HMAC256(jwtSecret))
                .withSubject("user details")
                .withIssuer("Facebook-clone")
                .build();
        return jwt.verify(token).getClaim("username").asString();
    }
}

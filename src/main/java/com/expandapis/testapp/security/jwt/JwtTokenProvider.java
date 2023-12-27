package com.expandapis.testapp.security.jwt;

import com.expandapis.testapp.exception.TestTaskApiException;
import com.expandapis.testapp.util.AppConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date validityDate = new Date(currentDate.getTime()
                + AppConstants.JWT_TOKEN_LIFE_TIME_MILLISECONDS);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(validityDate)
                .signWith(SignatureAlgorithm.HS512, AppConstants.JWT_TOKEN_SECRET_KEY)
                .compact();
    }

    public String getUsernameFromJwT(String token) {
        return Jwts.parser()
                .setSigningKey(AppConstants.JWT_TOKEN_SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getCredentialsFromJwT(String token) {
        return (String) Jwts.parser()
                .setSigningKey(AppConstants.JWT_TOKEN_SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("credentials");
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(AppConstants.JWT_TOKEN_SECRET_KEY)
                    .parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (SignatureException ex) {
            throw new TestTaskApiException(HttpStatus.BAD_REQUEST, "Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new TestTaskApiException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new TestTaskApiException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new TestTaskApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new TestTaskApiException(HttpStatus.BAD_REQUEST, "Expired or invalid JWT token");
        }
    }
}

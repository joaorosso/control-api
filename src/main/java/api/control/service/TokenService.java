package api.control.service;

import api.control.entity.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

@Service
public class TokenService {

    @Value("${api.jwt.expiration}")
    private String expiration;

    @Value("${api.jwt.secret}")
    private String secret;

    public String getToken(Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();
        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));
        return Jwts.builder()
                .setIssuer("ControlAPI")
                .setSubject(account.getId().toString())
                .setIssuedAt(today)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean isValid(String token) {
        try {
            Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(removeBearer(token));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public UUID getSubjectId(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(removeBearer(token)).getBody();
        return UUID.fromString(claims.getSubject());
    }

    private String removeBearer(String token) {
        if (token == null || !token.startsWith("Bearer")) return null;
        return token.substring(7);
    }
}

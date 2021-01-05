package dev.me.forum.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import dev.me.forum.modelo.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

  @Value("${forum.jwt.expiration}")
  private String expiration;

  @Value("${forum.jwt.secret}")
  private String secret;

  public String generateToken(Authentication authentication) {
    Usuario logged = (Usuario) authentication.getPrincipal();
    Date today = new Date();
    Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));

    return Jwts.builder().setIssuer("Api fórum").setSubject(logged.getId().toString()).setIssuedAt(today)
        .setExpiration(expirationDate).signWith(SignatureAlgorithm.HS256, secret).compact();
  }

  public boolean isTokenValid(String token) {
    try {
      Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
      return true;

    } catch (Exception e) {
      return false;
    }
  }

  public Long getUserId(String token) {
    Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
    return Long.parseLong(claims.getSubject());
  }

}

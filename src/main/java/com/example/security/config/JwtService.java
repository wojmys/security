package com.example.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.function.Function;

@Service
public class JwtService
{
   private static final String SECRET_KEY="aa8070f0f902afe6813d3f187910b6a527dca88e1b488aa32093ffb9187383c9";
   public String extractUsername(String token) {
      return null;
   }

   public <T> T extractClaim (String token, Function<Claims, T>claimsResolver){
      final Claims claims = extractAllClaims(token);
      return claimsResolver.apply(claims);
   }
   private Claims extractAllClaims(String token){
      return Jwts
               .parserBuilder()
               .setSigningKey(getSigninKey())
               .build()
               .parseClaimsJws(token)
               .getBody();
   }

   private Key getSigninKey()
   {
      byte [] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
      return Keys.hmacShaKeyFor(keyBytes);
   }
}

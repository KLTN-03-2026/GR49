package com.quoc.Movie_Ticket_Booking.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtProvide {

    private  SecretKey key = Keys.hmacShaKeyFor(JwtConstant.JWT_KEY.getBytes());

    public String generateToken(Authentication auth) {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles = populateAuthorities(authorities);

        String jwt = Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+86400000))
                .claim("authorities",roles)
                .claim("email",auth.getName())
                .signWith(key)
                .compact();

        return jwt;
    }

        public String getEmailFromJwtToken(String jwt) {

            // Kiểm tra null hoặc chuỗi không hợp lệ
            if (jwt == null || !jwt.startsWith("Bearer ")) {
                throw new IllegalArgumentException("Invalid JWT format");
            }

            jwt = jwt.substring(7);

            Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
            String email= String.valueOf(claims.get("email"));
            return email;
        }


    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auths = new HashSet<>();

        for (GrantedAuthority authority : authorities) {

            auths.add(authority.getAuthority());
        }
        return String.join(",", auths);
    }

}

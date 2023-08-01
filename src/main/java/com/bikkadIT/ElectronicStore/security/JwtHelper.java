package com.bikkadIT.ElectronicStore.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtHelper {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;      // kitne minute tak token valid rahega

    private String secret = "ElectronicStore";


    public String getUsernameFromToken(String token) {              // retrieve username from jwt token
        return getClaimFromToken(token, Claims::getSubject);
    }


    public Date getExpirationDateFromToken(String token) {          // retrieve expiration date from jwt token
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {

        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // for retrieving any info from token we will need secret key

    private Claims getAllClaimsFromToken(String token) {

        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    // check if the token has expired

    private Boolean isTokenExpired(String token) {

        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // genrate token for user

    public String genrateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();
        return doGenrateToken(claims, userDetails.getUsername());
    }

    //while creating the Token _
    //1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the Hs512 algorithm and secret key.
    //3. According ti JWS compact serializatin(https://tools.ietf.org/html/draft-ietf-jose)
    // compaction of the JWT to a URL-safe string

    private String doGenrateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }


    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {

        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}

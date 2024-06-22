package com.Photography.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class AdminJWTService
{
	
	private String secretKey;
	
	 @Autowired
	 private AdminService adminService;
	
	public AdminJWTService()
	{
		secretKey = generateSecretKey();
	}

	public String generateToken(String username)
	{
		Map<String,Object> claims=new HashMap<>();
		
		return Jwts
                .builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getKey(), Jwts.SIG.HS256)
                .compact();
	}
	
	public String generateSecretKey()
	{
		try {
			KeyGenerator kg = KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk = kg.generateKey();
			return Base64.getEncoder().encodeToString(sk.getEncoded());
		}
		catch (Exception e){
			throw new RuntimeException("Error Generating Secret Key " + e);
		}
	}
	
	private SecretKey getKey()
	{
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
	}

	public String extractUserName(String token)
	{
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
        		.verifyWith(getKey())
        		.build()
        		.parseSignedClaims(token)
        		.getPayload();
    }


    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        final LocalDateTime tokenInvalidationTime = adminService.getTokenInvalidationTime(username);
        if (tokenInvalidationTime == null) {
            return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        }
        Date tokenInvalidationDate = convertToDate(tokenInvalidationTime);
        return (username.equals(userDetails.getUsername()) 
                && !isTokenExpired(token)
                && extractIssuedAt(token).before(tokenInvalidationDate));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    private Date extractIssuedAt(String token) {
        return extractClaim(token, Claims::getIssuedAt);
    }
    
    private Date convertToDate(LocalDateTime localDateTime) {
        return localDateTime == null ? null : Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}

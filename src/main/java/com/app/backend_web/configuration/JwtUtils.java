package com.app.backend_web.configuration;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.app.backend_web.services.TokenBlacklistService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtUtils {


    private final TokenBlacklistService tokenBlacklistService;

    // Se recomienda que la clave secreta sea más larga (al menos 256 bits/32 caracteres para HS256)
    // Para simplificar, mantendremos tu clave, pero ten en cuenta la seguridad.
    private static final String SECRET_KEY = "clave-super-secreta-para-jwt-1234567890"; 
    private static final long EXPIRATION_TIME = 86400000; // 24 horas

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // 🚨 MODIFICADO: Ahora acepta UserDetails para agregar información al token
    public String generarToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        
        // Agregar el rol como un claim
        userDetails.getAuthorities().forEach(authority -> claims.put("rol", authority.getAuthority()));
        
        // Agregar el correo (Subject)
        return generarToken(claims, userDetails.getUsername());
    }

    private String generarToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject) // Correo o ID del usuario
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String obtenerCorreoDelToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validarToken(String token, UserDetails userDetails) {
        final String correo = obtenerCorreoDelToken(token);
        return (correo.equals(userDetails.getUsername()) && !isTokenExpired(token) && !tokenBlacklistService.estaElTokenEnListaNegra(token));
    }
    
    // Nueva validación de caducidad
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    // MODIFICADO: Sobrecarga para el filtro JWT
    public boolean validarToken(String token) {
        try {
            if(tokenBlacklistService.estaElTokenEnListaNegra(token)){
                return false;
            }
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            // El parse exitoso implica que no está expirado y la firma es válida.
            return true;
        } catch (JwtException e) {
            // Loguear el error de JWT (ej. expiración) si es necesario
            return false;
        }
    }
}
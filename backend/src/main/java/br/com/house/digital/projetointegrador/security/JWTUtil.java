package br.com.house.digital.projetointegrador.security;

import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.model.enums.UserType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Classe de utilidades para trabalhar com JSON Web Tokens.
 */
@Component
public class JWTUtil {

    /**
     * Validade para um token (30 dias em milissegundos).
     */
    private static final long JWT_VALIDITY = 2_592_000_000L;

    /**
     * Chave secreta gerada com algoritmo HS512 (SHA-512).
     */
    private static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    /**
     * Obtém o email em um token através da Claim de Subject.
     * @param token - token JWT
     * @return email armazenado no token.
     */
    public String getEmailFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Obtém a data de expiração de um token.
     * @param token token JWT
     * @return data de expiração do token.
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public Long getProfileIdFromToken(String token) {
        return getClaimFromToken(token, claims -> claims.get("pid", Long.class));
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Gera o token para um usuário.
     * @param user entidade de um usuário
     * @return token JWT
     */
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", user.getType().name().toLowerCase());
        claims.put("pid", user.getProfileId());
        return doGenerateToken(claims, user.getUsername());
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    /**
     * Gera um token JWT, definindo as Claims como Subject, data de criação e expiração, e assina com a chave secreta.
     * @param claims claims para o token
     * @param subject valor para armazenar como subject
     * @return token JWT
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        final long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(currentTimeMillis)) // data de geração
                .setExpiration(new Date(currentTimeMillis + JWT_VALIDITY)) // data de expiração
                .signWith(key)
                .compact();
    }

    /**
     * Valida um token JWT, checando a data de expiração e consistência dos dados.
     * @param token token JWT
     * @param user entidade do usuário autenticado
     * @return true se o token é valido, false senão.
     */
    public Boolean validateToken(String token, User user) {
        final Claims claims = getAllClaimsFromToken(token);
        final String email = claims.getSubject();
        final UserType type = UserType.valueOf(claims.get("type", String.class).toUpperCase());
        return (email.equals(user.getUsername()) && type.equals(user.getType()) && !isTokenExpired(token));
    }

}

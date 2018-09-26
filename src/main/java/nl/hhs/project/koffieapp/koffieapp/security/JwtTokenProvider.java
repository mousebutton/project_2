package nl.hhs.project.koffieapp.koffieapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import nl.hhs.project.koffieapp.koffieapp.exception.CustomJwtException;
import nl.hhs.project.koffieapp.koffieapp.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private String SECRET = "secret";
    private final long tokenExpiresInMS = 3_600_000; // 1 hour

    @PostConstruct
    protected void init() {
        SECRET = Base64.getEncoder().encodeToString(SECRET.getBytes());
    }

    /**
     * Create Jwt Token based on the email of the user and his roles.
     * @param email
     * @param roles
     * @return Jwt Token (String)
     */
    public String createToken(String email, List<Role> roles) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("auth", roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
        Date now = new Date();
        Date validTill = new Date(now.getTime() + tokenExpiresInMS);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validTill)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    /**
     * Get the username from the Jwt Token.
     * @param token
     * @return Username
     */
    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Resolve and get the token from the request.
     * @param request
     * @return Resolved Token (String)
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    /**
     * Check if the Token is valid.
     * @param token
     * @return boolean
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            throw new CustomJwtException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

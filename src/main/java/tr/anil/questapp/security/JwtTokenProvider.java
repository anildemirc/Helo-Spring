package tr.anil.questapp.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.SignatureException;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenProvider {

    @Value("${questapp.app.secret}")
    private String APP_SECRET;
    @Value("${questapp.expires.in}")
    private long EXPIRES_IN;

    //  Authentication authentication edeceğimiz user demek
    public String generateJwtToken(Authentication auth) {
        JwtUserDetails userDetails = (JwtUserDetails) auth.getPrincipal();
        Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);
        return Jwts.builder()
                .setSubject(Long.toString(userDetails.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();
    }

    public String generateJwtTokenByUsername(Long userId) {
        Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);
        return Jwts.builder()
                .setSubject(Long.toString(userId))
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();
    }

    public Long getUserIdFromJwt(String token) {
        return Long.parseLong(this.extractClaim(token,Claims::getSubject));
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
            return !isTokenExpired(token);
        } catch (MalformedJwtException e) {
            return false;
        } catch (ExpiredJwtException e) {
            return false;
        } catch (UnsupportedJwtException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }



    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = this.extractClaim(token,Claims::getExpiration);
        return expiration.before(new Date());
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(APP_SECRET)
                .parseClaimsJws(token)
                .getBody();
    }



}

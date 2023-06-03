package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.security;

import java.util.Date;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenGenerator {
	
	private static final Long JWT_EXPIRATION = 7000000000l;
	private static final String JWT_SECRET = "secret";
	
	public String generateToken(Authentication authentication) {
		String username = authentication.getName();
		
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + JWT_EXPIRATION);
		
		String token = Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS512, JWT_SECRET)
				.compact();
		
		return token;
	}
	
	public String getUsernameJWT(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(JWT_SECRET)
				.parseClaimsJws(token)
				.getBody();
		
		return claims.getSubject();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
			return true;
		}catch(Exception e) {
			throw new AuthenticationCredentialsNotFoundException("El token no es valido");
		}
		
	}

}

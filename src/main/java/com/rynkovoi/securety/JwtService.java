package com.rynkovoi.securety;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.rynkovoi.properties.JwtProperties;
import com.rynkovoi.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String USERNAME_CLAIM = "sub";
    private static final String ROLE_CLAIM = "role";
    private static final String EXPIRATION_CLAIM = "exp";

    private final JwtProperties jwtProperties;

    public String extractUsername(String jwtToken) {
        return getClaim(jwtToken, USERNAME_CLAIM).asString();
    }

    public String generateToken(UserDetails username, Role role) throws Exception {
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecretKey());
        return JWT.create()
                .withSubject(username.getUsername())
                .withClaim(ROLE_CLAIM, role.name())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
                .sign(algorithm);
    }

    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        String username = extractUsername(jwtToken);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken));
    }

    private boolean isTokenExpired(String jwtToken) {
        return getClaim(jwtToken, EXPIRATION_CLAIM).asDate().before(new Date());
    }

    private Claim getClaim(String jwtToken, String claimName) {
        JWT.require(Algorithm.HMAC256(jwtProperties.getSecretKey()))
                .build()
                .verify(jwtToken);
        return JWT.decode(jwtToken).getClaims().get(claimName);
    }
}

package com.pranavlearn.springboot;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TokenService {
//    private final JwtEncoder jwtEncoder;
//    public TokenService(JwtEncoder jwtEncoder) {
//        this.jwtEncoder=jwtEncoder;
//    }
//    public String generateToken(User user) {
//        Instant now=Instant.now();
//        List<String> roles=user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
//        JwtClaimsSet claims = JwtClaimsSet.builder()
//                .issuer("jwtIssuer")
//                .issuedAt(now)
//                .expiresAt(now.plus(20, ChronoUnit.SECONDS))
//                .subject(user.getUsername())
//                .claim("role", roles)
//                .build();
//        JwsHeader header=JwsHeader.with(MacAlgorithm.HS256).build();
//        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
//    }
}

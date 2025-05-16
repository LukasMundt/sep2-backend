package de.haw.se2.security.facade.impl;

import de.haw.se2.security.facade.api.AuthenticationFacade;
import de.haw.se2.speedrun.openapitools.model.Credentials;
import de.haw.se2.speedrun.openapitools.model.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("${openapi.speedrunsOpenAPI30.base-path:}")
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;

    private static final int HOUR_IN_SECONDS = 60 * 60;

    @Autowired
    public AuthenticationFacadeImpl(AuthenticationManager authenticationManager, JwtEncoder jwtEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
    }

    @Override
    public ResponseEntity<Void> restAuthDelete() {
        //https://docs.spring.io/spring-security/reference/servlet/authentication/logout.html#customizing-logout-uris

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Void> restAuthGet(){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<TokenResponse> restAuthPost(Credentials credentials) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword());
        Authentication authentication = authenticationManager.authenticate(authToken);

        Instant now = Instant.now();
        List<String> authorities = authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(HOUR_IN_SECONDS))
                .subject(authentication.getName())
                .claim("authorities", authorities)
                .build();

        String tokenValue = this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(tokenValue);
        tokenResponse.setExpiresIn(HOUR_IN_SECONDS);
        tokenResponse.setTokenType("Bearer");

        return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
    }
}

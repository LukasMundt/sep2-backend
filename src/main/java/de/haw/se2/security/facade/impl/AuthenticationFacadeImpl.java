package de.haw.se2.security.facade.impl;

import de.haw.se2.security.facade.api.AuthenticationFacade;
import de.haw.se2.security.logic.api.RegisterUseCase;
import de.haw.se2.speedrun.common.CustomizedModelMapper;
import de.haw.se2.speedrun.openapitools.model.LoginCredentials;
import de.haw.se2.speedrun.openapitools.model.RegisterCredentials;
import de.haw.se2.speedrun.openapitools.model.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final RegisterUseCase registerUseCase;
    private final CustomizedModelMapper mapper;

    private static final int HOUR_IN_SECONDS = 60 * 60;

    @Autowired
    public AuthenticationFacadeImpl(AuthenticationManager authenticationManager, JwtEncoder jwtEncoder, RegisterUseCase registerUseCase, CustomizedModelMapper mapper) {
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
        this.registerUseCase = registerUseCase;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<Void> restAuthLogoutPost() {
        //https://docs.spring.io/spring-security/reference/servlet/authentication/logout.html#customizing-logout-uris
        //TODO: Remove in the next api version
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<List<String>> restAuthGet(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return new ResponseEntity<>(authorities, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TokenResponse> restAuthLoginPost(LoginCredentials credentials) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword());
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

    @Override
    public ResponseEntity<Void> restAuthRegisterPost(RegisterCredentials credentials) {
        de.haw.se2.security.common.pojo.RegisterCredentials registerCredentials = mapper.map(credentials, de.haw.se2.security.common.pojo.RegisterCredentials.class);
        registerUseCase.registerUser(registerCredentials);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

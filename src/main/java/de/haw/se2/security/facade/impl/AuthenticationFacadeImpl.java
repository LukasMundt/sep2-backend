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
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

@Controller
@RequestMapping("${openapi.speedrunsOpenAPI30.base-path:}")
public class AuthenticationFacadeImpl implements AuthenticationFacade {

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
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}

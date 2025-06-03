package de.haw.se2.security.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    public static final String ADMIN_ROLE = "ADMIN";
    public static final String USER_ROLE = "USER";

    @Value("${jwt.public.key}")
    private RSAPublicKey publicKey;

    @Value("${jwt.private.key}")
    private RSAPrivateKey privateKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/insertSampleData",
                                "/up",
                                "/rest/api/games/*/categories",
                                "/rest/api/games/*/*/leaderboard",
                                "/rest/auth/register",
                                "/rest/auth/login",
                                "/rest/api/games/*",
                                "/rest/api/games/all",
                                "/swagger-ui*/**",
                                "/v3/api-docs*/**",
                                "/openapi.yaml",
                                "/rest/rss/getFeed/*",
                                "/"
                        ).permitAll()

                        //User access
                        .requestMatchers("/rest/api/games/*/*/submit").hasAuthority(USER_ROLE)
                        .requestMatchers("/getFeedUrl").hasAnyAuthority(USER_ROLE, ADMIN_ROLE)
                        .requestMatchers("/rest/auth", "/rest/auth/logout").hasAnyAuthority(USER_ROLE, ADMIN_ROLE)

                        //Admin access
                        .requestMatchers("/rest/api/reviews/verify").hasAuthority(ADMIN_ROLE)
                        .requestMatchers("/rest/api/reviews/unreviewed/*/*").hasAuthority(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.POST, "/rest/api/games/*/categories").hasAuthority(ADMIN_ROLE)
                        .requestMatchers("/rest/api/games/*/categories/*").hasAuthority(ADMIN_ROLE)
                        .requestMatchers("/rest/api/reviews/unreviewed/*").hasAnyAuthority(ADMIN_ROLE)
                        .requestMatchers("/rest/api/games/*").hasAuthority(ADMIN_ROLE)

                        // ab in die firewall mit dem rest alla
                        .anyRequest().authenticated()
                )

                .logout(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)

                .csrf(csrf -> csrf.ignoringRequestMatchers("/rest/auth/login", "/rest/auth/register"))

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))


                .httpBasic(Customizer.withDefaults())

                .oauth2ResourceServer(oauth -> oauth
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                )

                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                );


        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.publicKey).privateKey(this.privateKey).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
        grantedAuthoritiesConverter.setAuthorityPrefix(""); // or "ROLE_" if your authorities are prefixed

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}

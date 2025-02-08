package individuals.api.config;


import individuals.api.keyloack.KeycloakJwtConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwtConfigurer ->
                                jwtConfigurer.jwtAuthenticationConverter(jwt -> {
                                    KeycloakJwtConverter converter = new KeycloakJwtConverter();
                                    return new JwtAuthenticationToken(
                                            jwt,
                                            converter.convert(jwt).getAuthorities()
                                    );
                                })
                        )
                )
                .authorizeHttpRequests(auth ->
                        auth.anyRequest().authenticated()
                )
                .build();
    }
}
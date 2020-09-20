package com.github.tddiaz.springcognito.resourceserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoders;

@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http.cors().
                    and()
                        .httpBasic().disable()
                        .formLogin().disable()
                        .csrf().disable()
                        .authorizeRequests()
                            .anyRequest().authenticated()
                    .and()
                        .oauth2ResourceServer().jwt(jwt -> jwt.decoder(JwtDecoders.fromIssuerLocation(issuerUri)));
        ;
    }
}
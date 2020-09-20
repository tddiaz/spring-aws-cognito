package com.github.tddiaz.springcognito.resourceserver;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Configuration
@SuppressWarnings("all")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class JwtAuthFilter extends OncePerRequestFilter {

    private RemoteJWKSet remoteJWKSet;

    public JwtAuthFilter() throws MalformedURLException {
        URL JWKUrl = new URL("https://cognito-idp.us-east-2.amazonaws.com/us-east-2_pOEeJ29pX/.well-known/jwks.json");
        this.remoteJWKSet = new RemoteJWKSet(JWKUrl);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        final String header = req.getHeader("Authorization").replace("Bearer", "");

        try {
            final JWT jwt = JWTParser.parse(header);
            final JWSKeySelector keySelector = new JWSVerificationKeySelector(JWSAlgorithm.RS256, remoteJWKSet);

            final ConfigurableJWTProcessor jwtProcessor = new DefaultJWTProcessor();
            jwtProcessor.setJWSKeySelector(keySelector);

            final JWTClaimsSet claimsSet = jwtProcessor.process(jwt, null);

            // process roles (gropus in cognito)
            final List<String> groups = (List<String>) claimsSet.getClaim("cognito:groups");
            final List<GrantedAuthority> authorities = new ArrayList<>();
            groups.forEach(s -> {
                if ("group-1".equals(s)) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_VIEWER"));
                }
            });

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(claimsSet, null, authorities));

        } catch (Exception e) {
            // TODO: add error response to response servlet
        }

        chain.doFilter(req, res);
    }
}
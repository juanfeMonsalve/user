package com.bci.user.config;

import com.bci.user.UserRepository;
import com.bci.user.entity.UserEntity;
import com.bci.user.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        String username;
        if (authentication.getPrincipal() instanceof DefaultOidcUser) {
            DefaultOidcUser oidc = (DefaultOidcUser) authentication.getPrincipal();
            username = oidc.getEmail();
        } else {
            username = authentication.getName();
        }

        String jwt = jwtService.generateToken(username);

        response.setHeader("X-Auth-Token", jwt);
        response.setContentType("application/json");
        response.getWriter().write("{\"token\":\"" + jwt + "\"}");
        response.getWriter().flush();
    }
}

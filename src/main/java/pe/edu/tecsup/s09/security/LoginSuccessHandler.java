package pe.edu.tecsup.s09.security;

import java.io.IOException;
import java.util.stream.Collectors;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String roles = authentication.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.joining(", "));
        // Simple redirect to home after login; roles could be logged or used
        response.sendRedirect("/");
    }
}
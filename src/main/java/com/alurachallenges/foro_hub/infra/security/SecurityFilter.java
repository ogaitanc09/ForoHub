package com.alurachallenges.foro_hub.infra.security;

import com.alurachallenges.foro_hub.repositories.UserRepository;
import com.alurachallenges.foro_hub.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter
{

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException
    {
        // Lee un token
        var authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            var token = authHeader.replace("Bearer ", "");
            var subject = tokenService.getSubject(token);

            if(subject != null)
            {
                // Token valido
                var user = userRepository.findByUsername(subject);
                System.out.println(user);
                // Forzar el inicio de sesion
                var autenticacion = new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(autenticacion);
            }
        }

        filterChain.doFilter(request,response);
    }
}

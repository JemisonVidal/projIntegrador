package br.com.house.digital.projetointegrador.security;

import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.service.exceptions.DataIntegrityException;
import br.com.house.digital.projetointegrador.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    private static final String JWT_HEADER = "Authorization";
    private static final String JWT_PREFIX = "Bearer ";
    private static final int JWT_START = JWT_PREFIX.length();

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final JWTUtil jwtUtil;
    private final HandlerExceptionResolver resolver;

    @Autowired
    public JWTRequestFilter(UserDetailsServiceImpl userDetailsServiceImpl,
                            JWTUtil jwtUtil,
                            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.jwtUtil = jwtUtil;
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            final String header = request.getHeader(JWT_HEADER);
            if (header != null && header.startsWith(JWT_PREFIX)) {
                final String token = header.substring(JWT_START);
                final UsernamePasswordAuthenticationToken auth = getAuthentication(token);
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext()
                    .setAuthentication(auth); // insere o usuário no contexto de autenticação
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            resolver.resolveException(request, response, null, e);
        }
    }

    /**
     * Autenticar usuário a partir do token caso seja válido.
     *
     * @param token JWT
     * @return instância de autenticação para o Spring Security.
     * @throws DataIntegrityException caso token seja inválido ou expirado
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String token) throws DataIntegrityException {
        final String email = jwtUtil.getEmailFromToken(token);
        User user = (User) this.userDetailsServiceImpl.loadUserByUsername(email);
        if (!jwtUtil.validateToken(token, user)) {
            throw new DataIntegrityException("Invalid token.");
        }
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

}

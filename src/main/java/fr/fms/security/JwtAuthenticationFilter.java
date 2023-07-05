package fr.fms.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    //Méthode qui prend en charge la demande d'authentification
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    //Une fois authentifié spring sacurity fait appel à cette méthode
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        User springUser = (User) authResult.getPrincipal();
        //System.out.println(springUser);
        //dés à present, il faut créer le token Jwt pour l'utilisateur connecté
        String jwtToken = JWT.create()
                .withSubject(springUser.getUsername())
                .withExpiresAt(new Date( System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME ))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", springUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(Algorithm.HMAC256(SecurityConstants.SECRET));
        response.setHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + jwtToken);
    }
}

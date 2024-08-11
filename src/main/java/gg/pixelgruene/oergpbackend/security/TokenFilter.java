package gg.pixelgruene.oergpbackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.lang.Collections;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class TokenFilter extends OncePerRequestFilter {

    @Getter
    public int userid;
    @Getter
    public String mail;
    public String role;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        UserService userService = new UserService();

        //TODO: Loginmethod
        if(request.getRequestURI().equals("/login")||request.getRequestURI().equals("/api/users/create")) {
            doFilter(request, response, filterChain);
        }


        UserService userService1 = new UserService();

        Cookie[] cookies = request.getCookies();

        String token = null;

        for(Cookie cookie : cookies) {
            if(cookie.getName().equals("JWT")) {
                token = cookie.getValue();
                break;
            }
        }
        if(token != null) {
            Claims claims = userService.exctractClaims(token);
            userid= Integer.parseInt(claims.getSubject());
            mail = (String) claims.get("email");
            role = (String) claims.get("role");
        }

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userService.userController.getUsernameByEmail(mail), null, null);
        authentication.setDetails(userid);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);

    }
}

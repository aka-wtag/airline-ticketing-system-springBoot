package com.ats.config.jwt;

import com.ats.model.user.User;
import com.ats.service.JwtService;
import com.ats.service.UserService;
import com.ats.util.TokenType;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtHelper;
    private final UserService userService;

    @Autowired
    public JwtAuthenticationFilter(JwtService jwtHelper, UserService userService) {
        this.jwtHelper = jwtHelper;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String requestHeader = request.getHeader("Authorization");
        int userId = 0;
        String token = null;
        TokenType secret = TokenType.ACCESS;

        if(request.getRequestURI().equals("/users/refresh-token")){
            secret = TokenType.REFRESH;
        }

        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
            token = requestHeader.substring(7);
            try {
              userId = Integer.parseInt(this.jwtHelper.getUserIdFromToken(token, secret));
                logger.info("User ID-" +userId + " is authenticated");
            } catch (ExpiredJwtException e) {
                logger.info("Given jwt token is expired !!");
            } catch (MalformedJwtException e) {
                logger.info("Invalid Token");
            } catch (Exception e) {
                logger.info(e);
            }
        } else {
            logger.info("Authentication not present!");
        }

        if (userId != 0 && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = userService.loadUserById(userId);

            // checking token validation and checking if token is invalidated (logout) or not
            if (Objects.nonNull(user) && jwtHelper.validateToken(token, String.valueOf(user.getUserId()), user.getClass().getSimpleName(), secret) && jwtHelper.isTokenInBlacklist(token)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                    logger.info("Validation fails !!");
            }
        }

        filterChain.doFilter(request, response);
    }
}
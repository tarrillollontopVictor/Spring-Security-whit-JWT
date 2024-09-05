package com.apiUser.Filter;

import com.apiUser.Utils.JwtUtils;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtValidateToken extends OncePerRequestFilter {

   private JwtUtils jwtUtils;


   public JwtValidateToken(JwtUtils jwtUtils) {
      this.jwtUtils = jwtUtils;
   }

   @Override
   protected void doFilterInternal(@NonNull HttpServletRequest request,
                                   @NonNull HttpServletResponse response,
                                   @NonNull FilterChain filterChain) throws ServletException, IOException {

      String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

      if (jwtToken != null) {
         jwtToken = jwtToken.substring(7);

         DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);

         String username = jwtUtils.extractUsername(decodedJWT);
         String stringAuthorities = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString();

         Collection <? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(stringAuthorities);

         SecurityContext context = SecurityContextHolder.createEmptyContext();
         Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
         context.setAuthentication(authenticationToken);
         SecurityContextHolder.setContext(context);

      }
      filterChain.doFilter(request, response);
   }
}

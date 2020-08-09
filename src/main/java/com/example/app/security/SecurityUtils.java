package com.example.app.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class SecurityUtils {

   private static final Logger log = LoggerFactory.getLogger(SecurityUtils.class);
   private SecurityUtils() {}


   public static Optional<String> getCurrentUsername() {
      final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      if (authentication == null) {
         log.debug("no authentication in security context found");
         return Optional.empty();
      }

      String username = null;
      if (authentication.getPrincipal() instanceof UserDetails) {
         UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
         username = springSecurityUser.getUsername();
      } else if (authentication.getPrincipal() instanceof String) {
         username = (String) authentication.getPrincipal();
      }

      log.debug("found username '{}' in security context", username);
      log.debug("claims ::{}", getClaim(authentication));

      return Optional.ofNullable(username);
   }


   public static Optional<UserDetails> getCurrentAuthentication(){
      final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      UserDetails details;
      try{
         details = (UserDetails) authentication.getPrincipal();
      }catch (ClassCastException e){
         log.debug("no authentication in security context found");
         return Optional.empty();
      }
      return Optional.of(details);
   }

   private static Claims getClaim(Authentication token) {
      try {
         return Jwts.parser()
                 .parseClaimsJws(token.toString())
                 .getBody();
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }
}

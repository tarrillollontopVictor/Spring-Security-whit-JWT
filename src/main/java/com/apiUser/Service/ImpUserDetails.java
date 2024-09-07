package com.apiUser.Service;

import com.apiUser.Entity.UserEntity;
import com.apiUser.Exception.EmailNotFound;
import com.apiUser.Repository.UserRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ImpUserDetails implements UserDetailsService {

   UserRepository userRepository;

   public ImpUserDetails(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


      UserEntity userEntity = Optional.of(userRepository.getEmail(username))
                                      .get()
                                      .orElseThrow(() -> new EmailNotFound(" Not found username in database ", HttpStatus.NOT_FOUND));

      Set <GrantedAuthority> grantedAuthorities = new HashSet <>();
      userEntity.getRoles().stream()
              .forEach(roleEntity -> grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+roleEntity.getNameRole().name())));

      userEntity.getRoles().stream()
              .flatMap(roleEntity -> roleEntity.getPermissions().stream())
                .forEach(permission -> grantedAuthorities.add(new SimpleGrantedAuthority(permission.getNamePermission().name())));


      UserDetails user = User.builder()
              .username(userEntity.getName())
              .password(userEntity.getPassword())
              .authorities(grantedAuthorities)
              .build();


      return user;
   }
}

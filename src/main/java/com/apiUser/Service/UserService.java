package com.apiUser.Service;

import com.apiUser.Dto.Request.RequestCreateUser;
import com.apiUser.Dto.Request.RequestLogin;
import com.apiUser.Dto.Response.ResponseUserCredentials;
import com.apiUser.Entity.RoleEntity;
import com.apiUser.Entity.UserEntity;
import com.apiUser.Exception.IDRoleNotFound;
import com.apiUser.Repository.RoleRepository;
import com.apiUser.Repository.UserRepository;
import com.apiUser.Utils.JwtUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

   private AuthenticationManager authenticationManager;
   private PasswordEncoder passwordEncoder;
   private JwtUtils jwtUtils;
   private RoleRepository rolRepository;
   private UserRepository userRepository;

   public UserService(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, RoleRepository rolRepository, UserRepository userRepository) {
      this.authenticationManager = authenticationManager;
      this.passwordEncoder = passwordEncoder;
      this.jwtUtils = jwtUtils;
      this.rolRepository = rolRepository;
      this.userRepository = userRepository;
   }

   public ResponseUserCredentials login(RequestLogin requestLogin){

      String username = requestLogin.email();
      String password = requestLogin.password();

      Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

      String accessToken = jwtUtils.createToken(auth);

      return new ResponseUserCredentials(username,"user authenticate whit success",accessToken);
   }

   public ResponseUserCredentials singUp(RequestCreateUser requestCreateUser){


      RoleEntity rol = rolRepository.findById(2L).orElseThrow(() -> new IDRoleNotFound("Role ID not found in database", HttpStatus.NOT_FOUND));

      UserEntity user= UserEntity.builder()
                                 .name(requestCreateUser.name())
                                 .lastName(requestCreateUser.lastName())
                                 .password(passwordEncoder.encode(requestCreateUser.password()))
                                 .email(requestCreateUser.email())
                                 .city(requestCreateUser.city())
                                 .country(requestCreateUser.country())
                                 .roles(Set.of(rol))
                                 .build();
      userRepository.save(user);

      List <SimpleGrantedAuthority> authorities = new ArrayList <>();

      user.getRoles()
          .forEach( roleEntity -> {
                       authorities.add(new SimpleGrantedAuthority("ROLE_".concat(roleEntity.getNameRole().name())));
                    }
          );
      user.getRoles().stream()
          .flatMap(role -> role.getPermissions().stream())
          .forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getNamePermission().name())));


      Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword(),authorities);
      String token = jwtUtils.createToken(authentication);

      return new ResponseUserCredentials(user.getEmail(),"Create user whit success", token);
   }




}

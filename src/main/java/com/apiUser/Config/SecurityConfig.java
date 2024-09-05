package com.apiUser.Config;


import com.apiUser.Filter.JwtValidateToken;
import com.apiUser.Service.ImpUserDetails;
import com.apiUser.Utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

   @Autowired
   private JwtUtils jwtUtils;

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
     return http
              .csrf(csrf -> csrf.disable())
              .httpBasic(Customizer.withDefaults())
              .authorizeHttpRequests((authorize) -> authorize
                      .requestMatchers(HttpMethod.POST, "/api-user/get-user").permitAll()
                      .requestMatchers(HttpMethod.POST, "/api-user/create-user").permitAll()
                      .anyRequest().denyAll()
              )
              .addFilterBefore(new JwtValidateToken(jwtUtils), BasicAuthenticationFilter.class )
             .build();
   }

   @Bean
   public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
      return authenticationConfiguration.getAuthenticationManager();
   }

   @Bean
   public AuthenticationProvider authenticationProvider(ImpUserDetails impUserDetails){
      DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
      dao.setUserDetailsService(impUserDetails);
      dao.setPasswordEncoder(passwordEncoder());
      return dao;
   }

   @Bean
   public PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
   }



}

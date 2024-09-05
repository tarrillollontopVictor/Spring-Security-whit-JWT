package com.apiUser.Controller;


import com.apiUser.Dto.Request.RequestCreateUser;
import com.apiUser.Dto.Request.RequestLogin;
import com.apiUser.Dto.Response.ResponseUserCredentials;
import com.apiUser.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-user")

public class UserController {

   private UserService userService;

   public UserController(UserService userService) {
      this.userService = userService;
   }

   @PostMapping("/get-user")
   public ResponseEntity<ResponseUserCredentials> getUser(@RequestBody RequestLogin requestLogin){
      return ResponseEntity.status(HttpStatus.OK).body(userService.login(requestLogin));
   }

   @PostMapping("/create-user")
   public ResponseEntity<ResponseUserCredentials> createUser(@RequestBody RequestCreateUser createUser){
      return ResponseEntity.status(HttpStatus.OK).body(userService.singUp(createUser));
   }



}

package com.apiUser.Exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class IDRoleNotFound extends RuntimeException{

   HttpStatus httpStatus;
   public IDRoleNotFound(String message, HttpStatus httpStatus) {
      super(message);
      this.httpStatus = httpStatus;
   }

}

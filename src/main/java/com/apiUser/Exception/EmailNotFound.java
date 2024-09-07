package com.apiUser.Exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class EmailNotFound extends RuntimeException{

   HttpStatus httpStatus;
   public EmailNotFound(String message, HttpStatus httpStatus) {
      super(message);
      this.httpStatus = httpStatus;
   }
}

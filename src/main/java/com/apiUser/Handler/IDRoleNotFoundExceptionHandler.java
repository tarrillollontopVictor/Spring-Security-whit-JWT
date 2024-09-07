package com.apiUser.Handler;

import com.apiUser.Dto.Response.ResponseError;
import com.apiUser.Exception.IDRoleNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class IDRoleNotFoundExceptionHandler {


   @ExceptionHandler(value = IDRoleNotFound.class)
   public ResponseEntity<ResponseError> idRoleNotFound(IDRoleNotFound idException){

      ResponseError responseError = ResponseError.builder()
              .message(idException.getMessage())
              .code("ID_NOT_FOUND")
              .status(HttpStatus.NOT_FOUND.value())
              .build();

      return ResponseEntity.status(idException.getHttpStatus()).body(responseError);


   }



}

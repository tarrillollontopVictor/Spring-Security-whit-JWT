package com.apiUser.Handler;

import com.apiUser.Dto.Response.ResponseError;
import com.apiUser.Exception.EmailNotFound;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EmailNotFoundExceptionHandler {


   @ExceptionHandler(value = EmailNotFound.class)
   public ResponseEntity<ResponseError> emailNotFound(EmailNotFound emailNotFound){

      ResponseError responseError = ResponseError.builder()
              .code("EMAIL_NOT_FOUND")
              .status(emailNotFound.getHttpStatus().value())
              .message(emailNotFound.getMessage()).build();

      return ResponseEntity.status(emailNotFound.getHttpStatus()).body(responseError);

   }

   @ExceptionHandler(value = MethodArgumentNotValidException.class)
   public ResponseEntity<ResponseError> methodException(MethodArgumentNotValidException columnException){

      List<FieldError> fieldErrorList = columnException.getFieldErrors();
      ResponseError responseError = ResponseError.builder()
                                                 .code("COLUMN_INVALID_VALIDATION")
                                                 .status(columnException.getStatusCode().value())
                                                 .message(columnException.getFieldError().getDefaultMessage()).build();

      return ResponseEntity.status(columnException.getStatusCode()).body(responseError);

   }



}

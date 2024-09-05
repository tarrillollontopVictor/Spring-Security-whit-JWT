package com.apiUser.Dto.Request;

public record RequestCreateUser(

         String name,
         String lastName,
         String email,
         String password,
         String city,
         String country

) {

}

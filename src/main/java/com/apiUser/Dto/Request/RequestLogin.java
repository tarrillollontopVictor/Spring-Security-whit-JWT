package com.apiUser.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RequestLogin (

        @NotBlank(message = "Email is mandatory")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z]+\\.[A-Za-z]{3}(\\.[A-Za-z]{2})?$", message =
                "The email must contain letters, numbers or characters in the initial part, in the domain only letters and an extension of two to three characters")
        String email,

       @NotBlank(message = "Password is mandatory")
       @Size(min = 8, max = 20, message = "Password length should be between 8 and 20 characters")
       @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", message =
        "Password should contain at least one uppercase, one lowercase, one digit, and one special character")
       String password

) {

}

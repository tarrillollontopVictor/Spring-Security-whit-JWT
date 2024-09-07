package com.apiUser.Dto.Response;

import lombok.Builder;



@Builder
public record ResponseError(

        String message,
        String code,
        int status
) {

}

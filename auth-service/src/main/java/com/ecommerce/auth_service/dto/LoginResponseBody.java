package com.ecommerce.auth_service.dto;

import lombok.Data;

@Data
public class LoginResponseBody {

    private UserResponseBody userResponseBody;
    private String token;
}

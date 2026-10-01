package com.ecommerce.auth_service.dto;

import lombok.Data;

@Data
public class LoginRequestBody {
    private String username;
    private String password;
}

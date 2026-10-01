package com.ecommerce.auth_service.service;

import com.ecommerce.auth_service.dto.LoginRequestBody;
import com.ecommerce.auth_service.dto.LoginResponseBody;
import com.ecommerce.auth_service.dto.UserRequestBody;
import com.ecommerce.auth_service.dto.UserResponseBody;

public interface UserService {
    UserResponseBody registerUser(UserRequestBody payLoad);

    UserResponseBody createUser(UserRequestBody payLoad);

    LoginResponseBody loginUser(LoginRequestBody payLoad);

    boolean validateToken(String token);
}

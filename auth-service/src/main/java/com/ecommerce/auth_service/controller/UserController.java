package com.ecommerce.auth_service.controller;

import com.ecommerce.auth_service.dto.LoginRequestBody;
import com.ecommerce.auth_service.dto.LoginResponseBody;
import com.ecommerce.auth_service.dto.UserRequestBody;
import com.ecommerce.auth_service.dto.UserResponseBody;
import com.ecommerce.auth_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseBody> registerUser(@RequestBody UserRequestBody payLoad) {
        UserResponseBody result = userService.registerUser(payLoad);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<UserResponseBody> createUser(@RequestBody UserRequestBody payLoad) {
        UserResponseBody result = userService.createUser(payLoad);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseBody> loginUser(@RequestBody LoginRequestBody payLoad) {
        LoginResponseBody result = userService.loginUser(payLoad);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/validate")
    public ResponseEntity<Void> validateToken(@RequestParam("token") String token) {
        boolean isValid = userService.validateToken(token); // or wherever your validation lives
        if (isValid) {
            return ResponseEntity.ok().build(); // Returns 200 OK if valid
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Returns 401 if invalid
        }
    }
}

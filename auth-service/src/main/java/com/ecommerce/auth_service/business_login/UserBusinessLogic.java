package com.ecommerce.auth_service.business_login;

import com.ecommerce.auth_service.config.JwtService;
import com.ecommerce.auth_service.dto.LoginRequestBody;
import com.ecommerce.auth_service.dto.LoginResponseBody;
import com.ecommerce.auth_service.dto.UserRequestBody;
import com.ecommerce.auth_service.dto.UserResponseBody;
import com.ecommerce.auth_service.entities.User;
import com.ecommerce.auth_service.enums.ActiveStatus;
import com.ecommerce.auth_service.enums.RoleList;
import com.ecommerce.auth_service.repository.UserRepository;
import com.ecommerce.auth_service.service.UserService;
import jakarta.security.auth.message.config.AuthConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserBusinessLogic implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public UserResponseBody registerUser(UserRequestBody payLoad) {

        User newUser = new User();

        newUser.setUsername(payLoad.getUsername());
        newUser.setName(payLoad.getName());
        newUser.setEmail(payLoad.getEmail());
        newUser.setPassword(passwordEncoder.encode(payLoad.getPassword()));
        newUser.setPhone(payLoad.getPhone());
        newUser.setStatus(ActiveStatus.Active);
        newUser.setRole(RoleList.User);

        userRepository.save(newUser);

        UserResponseBody result = new UserResponseBody();
        result.setUsername(newUser.getUsername());
        result.setName(newUser.getName());
        result.setEmail(newUser.getEmail());
        result.setPhone(newUser.getPhone());
        result.setStatus(newUser.getStatus());
        result.setRole(newUser.getRole());

        return result;
    }

    @Override
    public UserResponseBody createUser(UserRequestBody payLoad) {
        User newUser = new User();

        newUser.setUsername(payLoad.getUsername());
        newUser.setName(payLoad.getName());
        newUser.setEmail(payLoad.getEmail());
        newUser.setPassword(passwordEncoder.encode(payLoad.getPassword()));
        newUser.setPhone(payLoad.getPhone());
        newUser.setStatus(ActiveStatus.Active);
        newUser.setRole(payLoad.getRole());

        userRepository.save(newUser);

        UserResponseBody result = new UserResponseBody();
        result.setUsername(newUser.getUsername());
        result.setName(newUser.getName());
        result.setEmail(newUser.getEmail());
        result.setPhone(newUser.getPhone());
        result.setStatus(newUser.getStatus());
        result.setRole(newUser.getRole());
        result.setUserId(newUser.getUserId());

        return result;
    }

    @Override
    public LoginResponseBody loginUser(LoginRequestBody payLoad) {

        Optional<User> userRecord = userRepository
                .findByUsername(payLoad.getUsername());

        if (userRecord.isPresent()) {

            User user = userRecord.get();

            UserResponseBody userResponseBody = new UserResponseBody();

            userResponseBody.setUsername(user.getUsername());
            userResponseBody.setName(user.getName());
            userResponseBody.setEmail(user.getEmail());
            userResponseBody.setPhone(user.getPhone());
            userResponseBody.setStatus(user.getStatus());
            userResponseBody.setRole(user.getRole());
            userResponseBody.setUserId(user.getUserId());

            String token = "";

            if(passwordEncoder.matches(payLoad.getPassword(), user.getPassword())) {
               token = jwtService.generateToken(payLoad);
            } else {
                throw new RuntimeException("Invalid password");
            }

            LoginResponseBody result = new LoginResponseBody();
            result.setUserResponseBody(userResponseBody);
            result.setToken(token);

            return result;
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public boolean validateToken(String token) {
        boolean result = jwtService.validateToken(token);
        return result;
    }

}

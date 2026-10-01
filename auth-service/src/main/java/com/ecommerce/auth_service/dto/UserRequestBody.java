package com.ecommerce.auth_service.dto;

import com.ecommerce.auth_service.enums.ActiveStatus;
import com.ecommerce.auth_service.enums.RoleList;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRequestBody {

    private String username;
    private String name;
    private String email;
    private String phone;
    private String password;
    private RoleList role;
    private LocalDate createdOn;
    private Integer updatedBy;
}

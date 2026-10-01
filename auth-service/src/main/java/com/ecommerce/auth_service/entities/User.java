package com.ecommerce.auth_service.entities;

import com.ecommerce.auth_service.enums.ActiveStatus;
import com.ecommerce.auth_service.enums.RoleList;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String username;
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String phone;
    private String password;
    @Enumerated(EnumType.STRING)
    private ActiveStatus status;
    @Enumerated(EnumType.STRING)
    private RoleList role;
    private LocalDate createdOn;
    private LocalDate updatedOn;
}

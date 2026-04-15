package com.employeemanagement.app.dto;

import com.employeemanagement.app.entity.Role;
import com.employeemanagement.app.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String fullName;
    private String email;
    private Role role;
    private Long managerId;
    private String managerName;
    private boolean emailVerified;
    private boolean active;
    private LocalDateTime createdAt;

    public User toEntity() {
        User user = new User();
        user.setId(this.id);
        user.setFullName(this.fullName);
        user.setEmail(this.email);
        user.setRole(this.role);
        user.setActive(this.active);
        user.setEmailVerified(this.emailVerified);
        return user;
    }
}
package com.campus.placement.dto;

import com.campus.placement.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    private String fullName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private Role role; // STUDENT by default, ADMIN for admin registration

    private String department;
    private String phone;
    private Double cgpa;
    private Integer graduationYear;
}

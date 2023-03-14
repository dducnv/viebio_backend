package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class RegisterDto {
    @NotNull(message = "Tên không được để trống!")
    @NotBlank(message = "Tên không được để trống!")
    private String name;
    @NotNull(message = "Username không được để trống!")
    @NotBlank(message = "Username không được để trống!")
    private String username;
    @NotNull(message = "Email không được để trống!")
    @NotBlank(message = "Email không được để trống!")
    private String email;
}

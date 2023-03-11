package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserInfoDto{
    private String avatar;
    @NotBlank(message = "Vui lòng nhập tên")
    @NotNull(message = "Vui lòng nhập tên")
    private String name;
    private String bio;
    @NotBlank(message = "Vui lòng nhập username")
    @NotNull(message = "Vui lòng nhập username")
    private String username;
    @NotBlank(message = "Vui lòng nhập email")
    @NotNull(message = "Vui lòng nhập eamil")
    @Email
    private String email;
    @NotBlank(message = "Vui lòng nhập email công khai")
    @NotNull(message = "Vui lòng nhập eamil công khai")
    @Email
    private String emailPublic;
}

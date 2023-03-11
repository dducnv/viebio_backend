package com.example.backend.dto;

import com.example.backend.entity.Item;
import com.example.backend.entity.Role;
import com.example.backend.entity.Theme;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
    private String avatar;
    @NotBlank(message = "Vui lòng nhập tên")
    @NotNull(message = "Vui lòng nhập tên")
    private String name;
    private String bio;
    private boolean verify;
    private boolean emailVerify;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<Item> items = new HashSet<>();
    private Theme theme;
    private Set<Role> roles = new HashSet<>();

}

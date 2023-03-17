package com.example.backend.service;

import com.example.backend.dto.*;
import com.example.backend.entity.User;

public interface UserService {
    UserInfoDto myInfo();
    UserInfoDto update(UpdateUserInfoDto updateUserInfoDto);
    UserInfoDto findByUsername(String username);
    UserInfoDto toUserDTO(User user);
    OtpResDto register(RegisterDto registerDto);
    OtpResDto getOtp(GetOtpDto getOtpDto);
    boolean checkUserExistWithEmail(String email);
    boolean checkUserExistWithUsername(String username);
    User findUserByEmail(String email);
    User findUserByUsernameOrEmail(String email, String username);
}

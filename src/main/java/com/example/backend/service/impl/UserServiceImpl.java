package com.example.backend.service.impl;

import com.example.backend.dto.*;
import com.example.backend.entity.Role;
import com.example.backend.entity.User;
import com.example.backend.exception.ApiRequestException;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.UserService;
import com.example.backend.utils.GeneratingPassword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service(value = "userService")
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {
    private final int expireTime = 60 * 1000 * 5;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public UserInfoDto myInfo(){
        User user = getUserFromToken();
       return toUserDTO(user);
    }
    @Override
    public UserInfoDto update(UpdateUserInfoDto updateUserInfoDto){
        User user = getUserFromToken();
        user.setAvatar(updateUserInfoDto.getAvatar());
        user.setName(updateUserInfoDto.getName());
        user.setBio(updateUserInfoDto.getBio());
        user.setUsername(updateUserInfoDto.getUsername());
        user.setEmail(updateUserInfoDto.getEmail());
        user.setEmailPublic(updateUserInfoDto.getEmailPublic());
        userRepository.save(user);
        return toUserDTO(user);
    }
    @Override
    public boolean register(RegisterDto registerDto){
        boolean findByEmail = checkUserExistWithEmail(registerDto.getEmail());
        if(findByEmail){
            throw new ApiRequestException("Email đã được sử dụng.");
        }
        boolean findByUsername = checkUserExistWithUsername(registerDto.getUsername());
        if(findByUsername){
            throw new ApiRequestException("Username đã được sử dụng.");
        }
        Role role = roleRepository.findByRoleName("USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        if(registerDto.getEmail().split("@")[0].equals("admin")){
            role = roleRepository.findByRoleName("ADMIN");
            roleSet.add(role);
        }
        User userSave = User.builder()
                .name(registerDto.getName())
                .username(registerDto.getUsername())
                .email(registerDto.getEmail())
                .roles(roleSet)
                .build();
        userRepository.save(userSave);
        return true;
    }
    @Override
    public OtpResDto getOtp(GetOtpDto getOtpDto){
        try {
            User user = findUserByEmail(getOtpDto.getEmail());
            Date expTime = new Date(System.currentTimeMillis() + expireTime);
            String password = String.valueOf(GeneratingPassword.generatePassword(12));
            user.setOneTimePassword(passwordEncoder.encode(password));
            user.setExpireTime(expTime);
            if (!user.isEmailVerify()) {
                user.setEmailVerify(true);
            }
            OtpResDto otpResDto = new OtpResDto(expTime,password);
            userRepository.save(user);
            return otpResDto;
        }catch (NullPointerException npx) {
            throw new ApiRequestException("Email chưa được đăng ký.");
        }
    };
    @Override
    public User findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }
    public User findUserByUsernameOrEmail(String email, String username) {
        User user = userRepository.findFirstByEmailOrUsername(email,username);
        return user;
    }
    @Override
    public boolean checkUserExistWithEmail(String email){
        User user = userRepository.findUserByEmail(email);
        if (user != null){
            return true;
        }
        return false;
    }
    @Override
    public boolean checkUserExistWithUsername(String username){
        User user = userRepository.findUserByUsername(username);
        if (user != null){
            return true;
        }
        return false;
    }

    public User getUserFromToken(){
        Object userInfo = SecurityContextHolder.getContext().getAuthentication().getName();
        return findUserByEmail(userInfo.toString());
    };
    @Override
    public UserInfoDto toUserDTO(User user){
        return UserInfoDto.builder()
                .avatar(user.getAvatar())
                .name(user.getName())
                .username(user.getUsername())
                .bio(user.getBio())
                .verify(user.isVerify())
                .emailVerify(user.isEmailVerify())
                .username(user.getUsername())
                .email(user.getEmail())
                .emailPublic(user.getEmailPublic())
                .createdAt(user.getCreatedAt())
                .theme(user.getTheme())
                .items(user.getItems())
                .roles(user.getRoles())
                .build();
    }
    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        });
        return authorities;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getOneTimePassword(), getAuthority(user));
    }
}

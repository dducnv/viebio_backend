package com.example.backend.api;

import com.example.backend.config.TokenProvider;
import com.example.backend.dto.*;
import com.example.backend.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.example.backend.config.constant.routes.apiv1.AuthRoutes.*;
import static com.example.backend.config.constant.routes.apiv1.MainRoutes.PREFIX_API_V1;

@RestController
@RequestMapping(PREFIX_API_V1)
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    private TokenProvider jwtTokenUtil;
    @Autowired
    AuthenticationManager authenticationManager;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
            @ApiImplicitParam(name = "X-Custom-Header", value = "A Custom Header", allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "my header example")
    })
    @RequestMapping(value = PREFIX_MY_INFO, method = RequestMethod.GET)
    @ApiOperation(value = "Lấy thông tin người dùng", notes = "Lấy thông tin người dùng")
    public ResponseEntity<?> myInfo() {
        UserInfoDto userInfoDto = userService.myInfo();
        return ResponseEntity.ok(
                ApiResDto.builder()
                        .message("Thành công!")
                        .data(userInfoDto)
                        .build()
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
            @ApiImplicitParam(name = "X-Custom-Header", value = "A Custom Header", allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "my header example")
    })
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    @ApiOperation(value = "Quyền truy cập admin", notes = "Test phân quyền hệ thống cho")
    public ResponseEntity<?> admin() {
        return ResponseEntity.ok("This is Admin");
    }

    @PreAuthorize("hasRole('USER')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
            @ApiImplicitParam(name = "X-Custom-Header", value = "A Custom Header", allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "my header example")
    })
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ApiOperation(value = "Quyền truy cập người dùng", notes = "Test phân quyền hệ thống cho")
    public ResponseEntity<?> user() {
        return ResponseEntity.ok("This is User");
    }

    @RequestMapping(value = PREFIX_REGISTER, produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDto registerDto) throws Exception {
        OtpResDto registerStatus = userService.register(registerDto);
        return ResponseEntity.ok(
                ApiResDto.builder()
                        .message("Đăng ký tài khoản thành công!")
                        .data(registerStatus)
                        .build()
        );
    }

    @RequestMapping(value = PREFIX_LOGIN, produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "Đăng nhập", notes = "Đăng nhập bằng email và mã otp")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDto loginDto) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        CredentialDto credentialDto = CredentialDto.builder()
                .accessToken(token)
                .expiresIn(System.currentTimeMillis() + TokenProvider.ONE_DAY * 7)
                .tokenType("Bearer")
                .scope("basic_info")
                .build();

        return ResponseEntity.ok(
                ApiResDto.builder()
                        .message("Đăng nhập thành công!")
                        .data(credentialDto)
                        .build()
        );
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @RequestMapping(value = PREFIX_UPDATE_USER, produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
    @ApiOperation(value = "Cập nhật thông tin người dùng", notes = "Cập nhật thông tin cơ bản người dùng")
    public ResponseEntity<?> update(@RequestBody @Valid UpdateUserInfoDto updateUserInfoDto) {
        UserInfoDto userInfoDto = userService.update(updateUserInfoDto);
        return ResponseEntity.ok(
                ApiResDto.builder()
                        .message("Thay đổi thông tin thành công!")
                        .data(userInfoDto)
                        .build()
        );
    }

    @ApiOperation(value = "Kiểm tra email đã tồn tại hay chưa", notes = "Dùng để check khi người dùng đăng ký")

    @RequestMapping(value = PREFIX_CHECK_EMAIL, produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity<?> checkEmail(@RequestBody @Valid EmailCheckDto emailCheckDto) {
        String message = "Email chưa được sử dụng!";
        boolean checkEmail = userService.checkUserExistWithEmail(emailCheckDto.getEmail());
        if(checkEmail){
            message = "Email đã được sử dụng!";
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                    ApiResDto.builder()
                            .message(message)
                            .build()
            );
        }
        return ResponseEntity.ok(
                ApiResDto.builder()
                        .message(message)
                        .build()
        );
    }

    @ApiOperation(value = "Kiểm tra username đã tồn tại hay chưa", notes = "Dùng để check khi người dùng đăng ký")

    @RequestMapping(value = PREFIX_CHECK_USERNAME, produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity<?> checkUsername(@RequestBody @Valid UsernameCheckDto usernameCheckDto) {
        String message = "Username chưa được sử dụng!";
        boolean checkUsername = userService.checkUserExistWithUsername(usernameCheckDto.getUsername());
        if(checkUsername){
            message = "Username đã được sử dụng!";
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                    ApiResDto.builder()
                            .message(message)
                            .build()
            );
        }
        return ResponseEntity.ok(
                ApiResDto.builder()
                        .message(message)
                        .build()
        );
    }

    @ApiOperation(value = "Lấy mã otp", notes = "Trả về mã otp và thời gian hêt hạn của mã")
    @RequestMapping(value = PREFIX_GET_OTP, produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity<?> getOTP(@RequestBody @Valid GetOtpDto getOtpDto) {
        OtpResDto otpResDto = userService.getOtp(getOtpDto);
        return ResponseEntity.ok(
                ApiResDto.builder()
                        .message("Lấy mã OTP thành công!")
                        .data(otpResDto)
                        .build()
        );
    }
}

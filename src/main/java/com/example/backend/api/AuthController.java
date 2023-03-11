package com.example.backend.api;

import com.example.backend.config.TokenProvider;
import com.example.backend.dto.*;
import com.example.backend.service.impl.UserServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
            @ApiImplicitParam(name = "X-Custom-Header", value = "A Custom Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "my header example")
    })
    @RequestMapping(value = PREFIX_MY_INFO,method = RequestMethod.GET)
    public ResponseEntity<?> myInfo() {
        return ResponseEntity.ok(userService.myInfo());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
            @ApiImplicitParam(name = "X-Custom-Header", value = "A Custom Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "my header example")
    })
    @RequestMapping(value = "/admin",method = RequestMethod.GET)
    public ResponseEntity<?> admin() {
        return ResponseEntity.ok("This is Admin");
    }

    @PreAuthorize("hasRole('USER')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
            @ApiImplicitParam(name = "X-Custom-Header", value = "A Custom Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "my header example")
    })
    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public ResponseEntity<?> user() {
        return ResponseEntity.ok("This is User");
    }

    @RequestMapping(value = PREFIX_REGISTER, produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDto registerDto) throws Exception {
        return ResponseEntity.ok(userService.register(registerDto));
    }
    @RequestMapping(value = PREFIX_LOGIN, produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody @Valid LoginDto loginDto)  {
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

            return ResponseEntity.ok(credentialDto);
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @RequestMapping(value = PREFIX_UPDATE_USER, produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity<?> update(@RequestBody @Valid UpdateUserInfoDto updateUserInfoDto)  {
        return ResponseEntity.ok(userService.update(updateUserInfoDto));
    }
    @RequestMapping(value = PREFIX_CHECK_EMAIL, produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity<?> checkEmail(@RequestBody @Valid EmailCheckDto emailCheckDto) {

        return ResponseEntity.ok(userService.checkUserExistWithEmail(emailCheckDto.getEmail()));
    }
    @RequestMapping(value = PREFIX_CHECK_USERNAME, produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity<?> checkUsername(@RequestBody  @Valid UsernameCheckDto usernameCheckDto) {
        return ResponseEntity.ok(userService.checkUserExistWithUsername(usernameCheckDto.getUsername()));
    }
    @RequestMapping(value = PREFIX_GET_OTP, produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity<?> getOTP(@RequestBody @Valid GetOtpDto getOtpDto) {
        System.out.println(getOtpDto.getEmail());
        return ResponseEntity.ok(userService.getOtp(getOtpDto));
    }



}

package com.example.backend.api;

import com.example.backend.dto.ApiResDto;
import com.example.backend.dto.UserInfoDto;
import com.example.backend.service.impl.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.example.backend.config.constant.routes.apiv1.ClientRoutes.PREFIX_USER_DETAILS;
import static com.example.backend.config.constant.routes.apiv1.MainRoutes.PREFIX_API_V1;

@RestController
@RequestMapping(PREFIX_API_V1)
@RequiredArgsConstructor
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @RequestMapping(value = PREFIX_USER_DETAILS, method = RequestMethod.GET)
    @ApiOperation(value = "Lấy thông tin người dùng", notes = "Lấy thông tin người dùng")
    public ResponseEntity<?> userDetails(@PathVariable String username) {
        UserInfoDto userInfoDto = userService.findByUsername(username);
        return ResponseEntity.ok(
                ApiResDto.builder()
                        .message("Thành công!")
                        .data(userInfoDto)
                        .build()
        );
    }
}

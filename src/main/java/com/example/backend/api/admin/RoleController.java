package com.example.backend.api.admin;

import com.example.backend.service.impl.RoleServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.example.backend.config.constant.routes.apiv1.AdminRoutes.*;

@RestController
@RequestMapping(value = PREFIX_ADMIN)
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Access Token", allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
        @ApiImplicitParam(name = "X-Custom-Header", value = "A Custom Header", allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "my header example")
})
public class RoleController {
    @Autowired
    RoleServiceImpl roleService;
    @RequestMapping(value = PREFIX_ROLE_LIST,method = RequestMethod.GET)
    @ApiOperation(value = "Lấy danh sách roles ", notes = "Lấy danh sách role (yêu cầu quyền Admin)")
    public ResponseEntity<?> listRole() {
        return ResponseEntity.ok(roleService.findAll());
    }
}

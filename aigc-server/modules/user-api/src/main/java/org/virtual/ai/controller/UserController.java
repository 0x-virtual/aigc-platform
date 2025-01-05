package org.virtual.ai.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.virtual.common.core.domain.R;
import org.virtual.ai.domain.LoginResponse;
import org.virtual.ai.domain.UserInfoResponse;
import org.virtual.ai.domain.bo.LoginRequest;
import org.virtual.ai.service.IUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;

    @PostMapping("/login")
    public R<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse value = userService.login(loginRequest);
        return R.ok(value);
    }

    @GetMapping("/me")
    public R<UserInfoResponse> info() {
        UserInfoResponse value = userService.info();
        return R.ok(value);
    }

}

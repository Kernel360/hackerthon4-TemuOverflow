package com.kernel360.ronaldo.TemuOverflow.user.controller;

import com.kernel360.ronaldo.TemuOverflow.user.dto.UserSignUpRequest;
import com.kernel360.ronaldo.TemuOverflow.user.entity.User;
import com.kernel360.ronaldo.TemuOverflow.user.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UserAuthController {

    private final UserAuthService userAuthService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestParam("email") String email,
                                         @RequestParam("password") String password,
                                         @RequestParam("nickname") String nickname,
                                         @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) throws Exception {
        UserSignUpRequest userSignUpRequest = new UserSignUpRequest(email, password, nickname, profileImage);
        User user = userAuthService.signUp(userSignUpRequest);
        return ResponseEntity.ok(user);
    }
}

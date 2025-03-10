package com.kernel360.ronaldo.TemuOverflow.user.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Getter
public class UserSignUpRequest {
    private String email;
    private String password;
    private String nickname;
    private MultipartFile profileImage;
}

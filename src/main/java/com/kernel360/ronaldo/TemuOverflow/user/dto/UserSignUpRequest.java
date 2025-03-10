package com.kernel360.ronaldo.TemuOverflow.user.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserSignUpRequest {
    private String email;
    private String password;
    private String nickname;
    private MultipartFile profileImage;
}

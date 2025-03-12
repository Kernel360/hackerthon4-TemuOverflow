package com.kernel360.ronaldo.TemuOverflow.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignUpResponse {
    private Long id;
    private String email;
    private String nickname;
}

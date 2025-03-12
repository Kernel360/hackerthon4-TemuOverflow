package com.kernel360.ronaldo.TemuOverflow.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserResponse {
    private Long id;
    private String email;
    private String nickname;
}

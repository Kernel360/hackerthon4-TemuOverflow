package com.kernel360.ronaldo.TemuOverflow.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreatePostResponse {
    private Long id;
    private String title;
    private String content;
    private Long userId;
}

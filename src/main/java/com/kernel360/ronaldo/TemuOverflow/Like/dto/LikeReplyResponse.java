package com.kernel360.ronaldo.TemuOverflow.Like.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeReplyResponse {
    private Long replyId;
    private Long userId;
    private boolean isLiked; // true: 좋아요, false: 좋아요 취소
    private String message;
}

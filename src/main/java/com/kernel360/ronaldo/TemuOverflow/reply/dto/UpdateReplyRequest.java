package com.kernel360.ronaldo.TemuOverflow.reply.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateReplyRequest {
    private String content;
}

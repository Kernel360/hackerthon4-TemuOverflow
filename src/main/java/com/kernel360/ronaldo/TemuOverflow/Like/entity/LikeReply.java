package com.kernel360.ronaldo.TemuOverflow.Like.entity;

import com.kernel360.ronaldo.TemuOverflow.reply.entity.Reply;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LikeReply {

    @Id
    @GeneratedValue
    @Column
    private Long id;

    private Long replyId;

    private Long userId;

    @ManyToOne
    @JoinColumn(name = "reply_id", nullable = false)
    private Reply reply;
}

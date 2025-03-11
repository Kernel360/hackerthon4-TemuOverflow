package com.kernel360.ronaldo.TemuOverflow.Like.entity;

import com.kernel360.ronaldo.TemuOverflow.post.entity.Post;
import com.kernel360.ronaldo.TemuOverflow.reply.entity.Reply;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LikeArticle {

    @Id
    @GeneratedValue
    @Column
    private Long id;

    private Long articleId;

    private Long userId;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}

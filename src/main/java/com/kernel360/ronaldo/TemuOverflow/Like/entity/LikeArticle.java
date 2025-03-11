package com.kernel360.ronaldo.TemuOverflow.Like.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
}

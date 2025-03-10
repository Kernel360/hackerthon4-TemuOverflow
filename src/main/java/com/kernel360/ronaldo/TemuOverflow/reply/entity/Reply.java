package com.kernel360.ronaldo.TemuOverflow.reply.entity;

import com.kernel360.ronaldo.TemuOverflow.article.entity.Article;
import com.kernel360.ronaldo.TemuOverflow.post.entity.Post;
import com.kernel360.ronaldo.TemuOverflow.user.entity.User;
import jakarta.persistence.*;

import static jakarta.persistence.FetchType.LAZY;

public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Post post;

    @ManyToOne(fetch = LAZY)
    private User user;

    private String content;
}

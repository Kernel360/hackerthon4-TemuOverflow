package com.kernel360.ronaldo.TemuOverflow.post.entity;

import com.kernel360.ronaldo.TemuOverflow.Like.entity.LikeArticle;
import com.kernel360.ronaldo.TemuOverflow.Like.entity.LikeReply;
import com.kernel360.ronaldo.TemuOverflow.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private User user;

    @Column(nullable = false, length = 45)
    private String title;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String content;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "is_solved")
    private Boolean isSolved = false;

    @Column(length = 45)
    private String category;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeArticle> likeArticles = new ArrayList<>();

    // 좋아요 개수를 반환하는 메서드
    public int getLikeCount() {
        return likeArticles ==null ? 0 : likeArticles.size();
    }
}

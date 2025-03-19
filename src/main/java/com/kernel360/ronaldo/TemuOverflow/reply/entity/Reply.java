package com.kernel360.ronaldo.TemuOverflow.reply.entity;

import com.kernel360.ronaldo.TemuOverflow.Like.entity.LikeReply;
import com.kernel360.ronaldo.TemuOverflow.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reply")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_id", nullable = false)
 //   @ManyToOne(fetch = LAZY)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(columnDefinition = "MEDIUMTEXT")
    // 또는 @Column(columnDefinition = "MEDIUMTEXT") 또는 @Column(columnDefinition = "LONGTEXT")
    private String content;

    @OneToMany(mappedBy = "reply", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeReply> likeReplies = new ArrayList<>();

    // 좋아요 개수를 반환하는 메서드
    public int getLikeCount() {
        return likeReplies == null ? 0 : likeReplies.size();
    }

}
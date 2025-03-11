package com.kernel360.ronaldo.TemuOverflow.post.dto;

import com.kernel360.ronaldo.TemuOverflow.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int likeCount;
//    private boolean isSolved;
//    private String category;


    // 데이터베이스에서 조회한 엔티티를 클라이언트에 반환할때 사용
    public static PostDto fromEntity(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .userId(post.getUserId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt() == null ? LocalDateTime.MIN : post.getUpdatedAt())
                .likeCount(post.getLikeCount())
//                .isSolved(post.getIsSolved() == null ? false : post.getIsSolved())
//                .category(post.getCategory() == null ? "null" : post.getCategory())
                .build();
    }
//
//    // 클라이언트에서 받은 DTO를 데이터베이스에 저장할때 사용
//    public Post toEntity() {
//        return Post.builder()
//                .id(this.id)
//                .userId(this.userId)
//                .title(this.title)
//                .content(this.content)
//                .createdAt(this.createdAt)
//                .updatedAt(this.updatedAt)
//                .isSolved(this.isSolved)
//                .category(this.category)
//                .build();
//    }
}
package com.kernel360.ronaldo.TemuOverflow.reply.dto;

import com.kernel360.ronaldo.TemuOverflow.post.entity.Post;
import com.kernel360.ronaldo.TemuOverflow.reply.entity.Reply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class
ReplyDto {
    private Long id;
    private Long postId;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String content;
    private int likeCount;


    // 데이터베이스에서 조회한 엔티티를 클라이언트에 반환할때 사용
    public static ReplyDto fromEntity(Reply reply) {
        return ReplyDto.builder()
                .id(reply.getId())
                .postId(reply.getPostId())
                .userId(reply.getUserId())
                .content(reply.getContent())
                .createdAt(reply.getCreatedAt())
                .updatedAt(reply.getUpdatedAt() == null ? LocalDateTime.MIN : reply.getUpdatedAt())
                .likeCount(reply.getLikeCount())
                .build();
    }

//    // 클라이언트에서 받은 DTO를 데이터베이스에 저장할때 사용
//    public Reply toEntity() {
//        return Reply.builder()
//                .id(this.id)
//                .postId(this.postId)
//                .userId(this.userId)
//                .content(this.content)
//                .build();
//    }

}

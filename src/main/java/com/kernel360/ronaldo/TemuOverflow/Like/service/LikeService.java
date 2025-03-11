package com.kernel360.ronaldo.TemuOverflow.Like.service;

import com.kernel360.ronaldo.TemuOverflow.Like.dto.LikeArticleResponse;
import com.kernel360.ronaldo.TemuOverflow.Like.dto.LikeReplyResponse;
import com.kernel360.ronaldo.TemuOverflow.Like.dto.LikeRequest;
import com.kernel360.ronaldo.TemuOverflow.Like.entity.LikeArticle;
import com.kernel360.ronaldo.TemuOverflow.Like.entity.LikeReply;
import com.kernel360.ronaldo.TemuOverflow.Like.repository.LikeArticleRepository;
import com.kernel360.ronaldo.TemuOverflow.Like.repository.LikeReplyRepository;
import com.kernel360.ronaldo.TemuOverflow.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeArticleRepository likeArticleRepository;
    private final LikeReplyRepository likeReplyRepository;
    private final PostRepository postRepository;

    public LikeArticleResponse likeArticle(Long userId, LikeRequest likeArticleRequest) {
        Long articleId = likeArticleRequest.getObjectId();

        boolean articleExists = postRepository.existsById(articleId);
        if (!articleExists) {
            throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
        }

        // 사용자가 이미 해당 게시글에 좋아요를 눌렀는지 확인
        Optional<LikeArticle> existingLike = likeArticleRepository.findByArticleIdAndUserId(articleId, userId);

        if (existingLike.isPresent()) {
            // 이미 좋아요를 눌렀다면 좋아요 취소
            likeArticleRepository.delete(existingLike.get());
            return new LikeArticleResponse(articleId, userId, false, "좋아요가 취소되었습니다.");
        }

        // 좋아요 저장
        LikeArticle likeArticle = LikeArticle.builder()
                .articleId(articleId)
                .userId(userId)
                .build();
        likeArticleRepository.save(likeArticle);

        return new LikeArticleResponse(articleId, userId, true, "좋아요를 눌렀습니다.");
    }

    public LikeReplyResponse likeReply(Long userId, LikeRequest likeReplyRequest) {
        Long replyId = likeReplyRequest.getObjectId();

        boolean replyExists = postRepository.existsById(replyId);
        if (!replyExists) {
            throw new IllegalArgumentException("존재하지 않는 댓글입니다.");
        }

        // 사용자가 이미 해당 게시글에 좋아요를 눌렀는지 확인
        Optional<LikeReply> existingLike = likeReplyRepository.findByReplyIdAndUserId(replyId, userId);

        if (existingLike.isPresent()) {
            // 이미 좋아요를 눌렀다면 좋아요 취소
            likeReplyRepository.delete(existingLike.get());
            return new LikeReplyResponse(replyId, userId, false, "좋아요가 취소되었습니다.");
        }

        // 좋아요 저장
        LikeReply likeReply = LikeReply.builder()
                .replyId(replyId)
                .userId(userId)
                .build();
        likeReplyRepository.save(likeReply);

        return new LikeReplyResponse(replyId, userId, true, "좋아요를 눌렀습니다.");
    }
}

package com.kernel360.ronaldo.TemuOverflow.Like.repository;

import com.kernel360.ronaldo.TemuOverflow.Like.entity.LikeReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeReplyRepository extends JpaRepository<LikeReply, Long> {

    Optional<LikeReply> findByReplyIdAndUserId(Long replyId, Long userId);
}

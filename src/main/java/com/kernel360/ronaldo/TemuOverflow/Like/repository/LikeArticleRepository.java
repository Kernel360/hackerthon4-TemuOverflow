package com.kernel360.ronaldo.TemuOverflow.Like.repository;

import com.kernel360.ronaldo.TemuOverflow.Like.entity.LikeArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeArticleRepository extends JpaRepository<LikeArticle, Long> {
    Optional<LikeArticle> findByArticleIdAndUserId(Long articleId, Long userId);
}

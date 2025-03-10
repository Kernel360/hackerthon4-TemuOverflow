package com.kernel360.ronaldo.TemuOverflow.post.repository;

import com.kernel360.ronaldo.TemuOverflow.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}

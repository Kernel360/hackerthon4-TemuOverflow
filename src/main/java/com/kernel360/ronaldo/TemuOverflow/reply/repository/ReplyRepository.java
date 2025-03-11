package com.kernel360.ronaldo.TemuOverflow.reply.repository;

import com.kernel360.ronaldo.TemuOverflow.reply.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByPostId(Long postId);
}

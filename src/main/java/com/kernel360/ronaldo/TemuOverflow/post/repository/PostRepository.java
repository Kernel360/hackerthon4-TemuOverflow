package com.kernel360.ronaldo.TemuOverflow.post.repository;

import com.kernel360.ronaldo.TemuOverflow.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {


    @Query(value = "SELECT * FROM post LIMIT 1 OFFSET :index", nativeQuery = true)
    Post findPostByIndex(@Param("index") int index);


    List<Post> findByTitleContainingOrContentContaining(String title, String content);

}

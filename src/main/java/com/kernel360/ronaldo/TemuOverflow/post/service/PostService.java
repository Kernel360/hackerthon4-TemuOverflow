package com.kernel360.ronaldo.TemuOverflow.post.service;

import com.kernel360.ronaldo.TemuOverflow.post.entity.Post;
import com.kernel360.ronaldo.TemuOverflow.post.repository.PostRepository;
import com.kernel360.ronaldo.TemuOverflow.user.repository.UserRepository;
import com.kernel360.ronaldo.TemuOverflow.user.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserAuthService userAuthService;

    // 게시글 생성
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    // 전체 게시글 조회
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // 게시글 상세 조회
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
    }

    // 게시글 수정
    public Post updatePost(Long id, Post updatedPost) {
        Post post = getPostById(id);
        post.setTitle(updatedPost.getTitle());
        post.setContent(updatedPost.getContent());
        post.setCategory(updatedPost.getCategory());
        post.setIsSolved(updatedPost.getIsSolved());
        post.setUpdatedAt(updatedPost.getUpdatedAt());
        return postRepository.save(post);
    }

    // 게시글 삭제
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    // 랜덤으로 게시글 조회(랜덤 에러)
    public Post getRandomPost(Long userId) {

    }
}
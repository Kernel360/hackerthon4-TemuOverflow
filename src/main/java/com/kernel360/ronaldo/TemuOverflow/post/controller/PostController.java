package com.kernel360.ronaldo.TemuOverflow.post.controller;

import com.kernel360.ronaldo.TemuOverflow.post.dto.PostDto;
import com.kernel360.ronaldo.TemuOverflow.post.entity.Post;
import com.kernel360.ronaldo.TemuOverflow.post.service.PostService;
import com.kernel360.ronaldo.TemuOverflow.user.service.UserAuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserAuthService userAuthService;

    // 게시글 생성 (POST)
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        Post post = postService.createPost(postDto.toEntity());
        return new ResponseEntity<>(PostDto.fromEntity(post), HttpStatus.CREATED);
    }

    // 전체 게시글 조회 (GET)
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> posts = postService.getAllPosts().stream()
                .map(PostDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(posts);
    }

    // 게시글 상세 조회 (GET)
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(PostDto.fromEntity(post));
    }

    // 게시글 수정 (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id, @RequestBody PostDto postDto) {
        Post post = postService.updatePost(id, postDto.toEntity());
        return ResponseEntity.ok(PostDto.fromEntity(post));
    }

    // 게시글 삭제 (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/random")
    public ResponseEntity<PostDto> getRandomPosts(HttpServletRequest request) {
        Long userId = userAuthService.getUserIdFromToken(request);
        return ResponseEntity.ok(PostDto.fromEntity(postService.getRandomPost(userId)));

    // 게시글 검색 (GET)
    @GetMapping("/search")
    public ResponseEntity<List<PostDto>> searchPosts(@RequestParam String keyword) {
        List<PostDto> posts = postService.searchPosts(keyword).stream()
                .map(PostDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(posts);

    }
}
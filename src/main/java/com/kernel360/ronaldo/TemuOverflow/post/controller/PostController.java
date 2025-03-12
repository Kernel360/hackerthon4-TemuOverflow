package com.kernel360.ronaldo.TemuOverflow.post.controller;

import com.kernel360.ronaldo.TemuOverflow.post.dto.CreatePostRequest;
import com.kernel360.ronaldo.TemuOverflow.post.dto.CreatePostResponse;
import com.kernel360.ronaldo.TemuOverflow.post.dto.PostDto;
import com.kernel360.ronaldo.TemuOverflow.post.dto.UpdatePostRequest;
import com.kernel360.ronaldo.TemuOverflow.post.entity.Post;
import com.kernel360.ronaldo.TemuOverflow.post.service.PostService;
import com.kernel360.ronaldo.TemuOverflow.user.service.UserAuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;
    private final UserAuthService userAuthService;

    // 게시글 생성 (POST)
    @PostMapping
    public ResponseEntity<PostDto> createPost(HttpServletRequest request, @RequestBody CreatePostRequest createPostRequest) {
        Long userId = userAuthService.getUserIdFromToken(request);
        PostDto postDto = postService.createPost(userId, createPostRequest);
        return new ResponseEntity<>(postDto, HttpStatus.CREATED);
    }

//    // 전체 게시글 조회 (GET)
//    @GetMapping
//    public ResponseEntity<List<PostDto>> getAllPosts() {
//        List<PostDto> posts = postService.getAllPosts().stream()
//                .map(PostDto::fromEntity)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(posts);
//    }

    // 게시글 상세 조회 (GET)
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(HttpServletRequest request, @PathVariable Long id) {
        //Long userId = userAuthService.getUserIdFromToken(request);
        PostDto postDto = postService.getPostById(request, id);
        return ResponseEntity.ok(postDto);
    }

    // 게시글 수정 (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id, @RequestBody UpdatePostRequest updatePostRequest) {
        PostDto postDto = postService.updatePost(id, updatePostRequest);
        return ResponseEntity.ok(postDto);
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
    }


    // 게시판 검색 && 페이지네이션
    @GetMapping
    public ResponseEntity<Page<PostDto>> getPosts(@RequestParam(required = false) String keyword, Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "id"));
        Page<Post> posts;
        if (keyword != null && !keyword.isEmpty()) {
            posts = postService.searchPosts(keyword, pageRequest);
        } else {
            posts = postService.getPagedPosts(pageRequest);
        }
        Page<PostDto> postDtos = posts.map(PostDto::fromEntity);
        return ResponseEntity.ok(postDtos);
    }


    @PatchMapping("/change-status/{id}")
    public ResponseEntity<PostDto> changeStatus(HttpServletRequest request, @PathVariable Long id) {
        log.info("controller로 왔음 상태변경");
        Long userId = userAuthService.getUserIdFromToken(request);
        return ResponseEntity.ok(postService.changeStatus(userId, id));
    }
}
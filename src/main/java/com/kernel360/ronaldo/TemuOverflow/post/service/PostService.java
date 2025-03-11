package com.kernel360.ronaldo.TemuOverflow.post.service;

import com.amazonaws.services.kms.model.CreateAliasRequest;
import com.kernel360.ronaldo.TemuOverflow.post.dto.CreatePostRequest;
import com.kernel360.ronaldo.TemuOverflow.post.dto.CreatePostResponse;
import com.kernel360.ronaldo.TemuOverflow.post.dto.PostDto;
import com.kernel360.ronaldo.TemuOverflow.post.dto.UpdatePostRequest;
import com.kernel360.ronaldo.TemuOverflow.post.entity.Post;
import com.kernel360.ronaldo.TemuOverflow.post.repository.PostRepository;
import com.kernel360.ronaldo.TemuOverflow.reply.dto.ReplyDto;
import com.kernel360.ronaldo.TemuOverflow.reply.entity.Reply;
import com.kernel360.ronaldo.TemuOverflow.user.repository.UserRepository;
import com.kernel360.ronaldo.TemuOverflow.user.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserAuthService userAuthService;
    private final UserRepository userRepository;

    // 게시글 생성
    public PostDto createPost(Long userId, CreatePostRequest createPostRequest) {
        Post post = Post.builder()
                .user(userRepository.findById(userId).orElse(null))
                .title(createPostRequest.getTitle())
                .content(createPostRequest.getContent())
                .createdAt(LocalDateTime.now())
                .build();
        postRepository.save(post);
        return PostDto.fromEntity(post);
    }

    // 전체 게시글 조회
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // 게시글 상세 조회 -> id는 postId임
    public PostDto getPostById(Long userId, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));

        boolean isLikedByCurrentUser = post.getLikeArticles().stream()
                .anyMatch(likeArticle -> likeArticle.getUserId().equals(userId));

        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .userId(post.getUser().getId())
                .userNickname(post.getUser().getNickname())
                .userProfileImageUrl(post.getUser().getProfileImageUrl())
                .likeCount(post.getLikeCount())
                .isLikedByCurrentUser(isLikedByCurrentUser)
                .build();
    }

    // 게시글 수정
    public PostDto updatePost(Long id, UpdatePostRequest updatePostRequest) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setTitle(updatePostRequest.getTitle());
        post.setContent(updatePostRequest.getContent());
        post.setUpdatedAt(LocalDateTime.now());
//        post.setCategory(updatedPost.getCategory());
//        post.setIsSolved(updatedPost.getIsSolved());
        postRepository.save(post);
        return PostDto.fromEntity(post);
    }

    // 게시글 삭제
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }


    // 랜덤으로 게시글 조회(랜덤 에러)
    public Post getRandomPost(Long userId) {
        long count = postRepository.count();
        if (count == 0) {
            throw new IllegalStateException("저장된 Article이 전무합니다.");
        }

        int randomIndex = (int) (Math.random() * count);  // 0부터 count-1까지 랜덤한 숫자
        return postRepository.findPostByIndex(randomIndex);
    }


    // 게시글 검색
    public List<Post> searchPosts(String keyword) {
        return postRepository.findByTitleContainingOrContentContaining(keyword, keyword);
    }

    // 페이지네이션
    public Page<Post> getPagedPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }
}




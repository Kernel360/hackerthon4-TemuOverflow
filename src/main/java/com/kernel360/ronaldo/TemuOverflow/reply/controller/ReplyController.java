package com.kernel360.ronaldo.TemuOverflow.reply.controller;

import com.kernel360.ronaldo.TemuOverflow.Like.repository.LikeReplyRepository;
import com.kernel360.ronaldo.TemuOverflow.chat.dto.ChatRequest;
import com.kernel360.ronaldo.TemuOverflow.chat.service.ChatService;
import com.kernel360.ronaldo.TemuOverflow.post.entity.Post;
import com.kernel360.ronaldo.TemuOverflow.post.service.PostService;
import com.kernel360.ronaldo.TemuOverflow.reply.dto.CreateReplyRequest;
import com.kernel360.ronaldo.TemuOverflow.reply.dto.ReplyDto;
import com.kernel360.ronaldo.TemuOverflow.reply.dto.UpdateReplyRequest;
import com.kernel360.ronaldo.TemuOverflow.reply.entity.Reply;
import com.kernel360.ronaldo.TemuOverflow.reply.repository.ReplyRepository;
import com.kernel360.ronaldo.TemuOverflow.reply.service.ReplyService;
import com.kernel360.ronaldo.TemuOverflow.user.entity.User;
import com.kernel360.ronaldo.TemuOverflow.user.repository.UserRepository;
import com.kernel360.ronaldo.TemuOverflow.user.service.UserAuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reply")
@RequiredArgsConstructor
@Slf4j
public class ReplyController {

    private final ReplyService replyService;
    private final PostService postService;
    private final ChatService chatService;
    private final UserAuthService userAuthService;
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;
    private final LikeReplyRepository likeReplyRepository;

    // 댓글 생성 (POST)
    @PostMapping
    public ResponseEntity<ReplyDto> createReply(HttpServletRequest request, @RequestBody CreateReplyRequest createReplyRequest) {
        Long userId = userAuthService.getUserIdFromToken(request);
        ReplyDto replyDto = replyService.createReply(userId, createReplyRequest);
        return new ResponseEntity<>(replyDto, HttpStatus.CREATED);
    }

    // 특정 게시글(PostId)에 대한 댓글 리스트 조회 (GET)
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<ReplyDto>> getRepliesByPostId(HttpServletRequest request, @PathVariable Long postId) {
        Long userId = userAuthService.getUserIdFromToken(request);

        List<Reply> replies = replyService.getRepliesByPostId(postId);

        List<ReplyDto> replyDtos = replies.stream()
                .map(reply -> {
                    ReplyDto dto = ReplyDto.fromEntity(reply);
                    boolean isLiked = reply.getLikeReplies().stream()
                            .anyMatch(likeReply -> likeReply.getUserId().equals(userId));

                    return new ReplyDto(
                            dto.getId(),
                            dto.getPostId(),
                            dto.getUserId(),
                            dto.getUserNickname(),
                            dto.getUserProfileImageUrl(),
                            dto.getCreatedAt(),
                            dto.getUpdatedAt(),
                            dto.getContent(),
                            dto.getLikeCount(),
                            isLiked  // 현재 사용자가 좋아요를 눌렀는지 여부 추가
                    );
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(replyDtos);
    }

    // 게시글 상세 조회 (GET)
    @GetMapping("/{id}")
    public ResponseEntity<ReplyDto> getPostById(@PathVariable Long id) {
        Reply reply = replyService.getReplyById(id);
        return ResponseEntity.ok(ReplyDto.fromEntity(reply));
    }

    // 댓글 수정 (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<ReplyDto> updateReply(HttpServletRequest request, @PathVariable Long id, @RequestBody UpdateReplyRequest updateReplyRequest) {
        Long userId = userAuthService.getUserIdFromToken(request);
        ReplyDto replyDto = replyService.updateReply(userId, id, updateReplyRequest);
        return ResponseEntity.ok(replyDto);
    }

    // 댓글 삭제 (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        replyService.deletePost(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/ai/{postId}")
    public Mono<ResponseEntity<Reply>> createAiReply(@PathVariable Long postId) {
        try {
            // 1. 게시물 조회
            Post post = postService.getPostById(postId);

            // 2. AI에게 질문할 메시지 구성
            String aiPrompt = String.format(
                    "다음 질문에 대한 답변을 300자 정도로 제공해주세요:\n\n제목: %s\n\n내용: %s ",
                    post.getTitle(),
                    post.getContent()
            );

            ChatRequest chatRequest = new ChatRequest(aiPrompt);

            User userAI = userRepository.findById(7777777L).orElse(null);

            // 3. AI 서비스에 요청하고 결과를 댓글로 저장
            return chatService.processChat(chatRequest)
                    .map(chatResponse -> {
                        // AI 응답으로 댓글 생성
                        Reply reply = Reply.builder()
                                .postId(postId)
                                .user(userAI) // AI 사용자 ID (시스템 사용자나 AI 전용 사용자 ID 설정)
                                .content(chatResponse.getReply())
                                .createdAt(LocalDateTime.now())
                                .updatedAt(LocalDateTime.now())
                                .build();
                        replyRepository.save(reply);

                        return ResponseEntity.ok(reply);
                    })
                    .onErrorResume(e -> {
                        log.error("AI 응답 처리 중 오류 발생", e);
                        return Mono.just(ResponseEntity.internalServerError().build());
                    });
        } catch (Exception e) {
            log.error("게시물 조회 중 오류 발생", e);
            return Mono.just(ResponseEntity.notFound().build());
        }
    }


}

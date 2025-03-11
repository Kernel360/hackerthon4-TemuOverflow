package com.kernel360.ronaldo.TemuOverflow.reply.controller;

import com.kernel360.ronaldo.TemuOverflow.reply.dto.ReplyDto;
import com.kernel360.ronaldo.TemuOverflow.reply.entity.Reply;
import com.kernel360.ronaldo.TemuOverflow.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    // 댓글 생성 (POST)
    @PostMapping
    public ResponseEntity<ReplyDto> createReply(@RequestBody ReplyDto replyDto) {
        Reply reply = replyService.createReply(replyDto.toEntity());
        return new ResponseEntity<>(ReplyDto.fromEntity(reply), HttpStatus.CREATED);
    }

    // 특정 게시글(PostId)에 대한 댓글 리스트 조회 (GET)
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<ReplyDto>> getRepliesByPostId(@PathVariable Long postId) {
        List<Reply> replies = replyService.getRepliesByPostId(postId);
        List<ReplyDto> replyDtos = replies.stream()
                .map(ReplyDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(replyDtos);
    }

    // 댓글 수정 (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<ReplyDto> updateReply(@PathVariable Long id, @RequestBody ReplyDto replyDto) {
        Reply reply = replyService.updateReply(id, replyDto.toEntity());
        return ResponseEntity.ok(replyDto.fromEntity(reply));
    }

    // 게시글 삭제 (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        replyService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

}

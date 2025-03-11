package com.kernel360.ronaldo.TemuOverflow.reply.service;

import com.kernel360.ronaldo.TemuOverflow.post.entity.Post;
import com.kernel360.ronaldo.TemuOverflow.reply.dto.CreateReplyRequest;
import com.kernel360.ronaldo.TemuOverflow.reply.dto.ReplyDto;
import com.kernel360.ronaldo.TemuOverflow.reply.dto.UpdateReplyRequest;
import com.kernel360.ronaldo.TemuOverflow.reply.entity.Reply;
import com.kernel360.ronaldo.TemuOverflow.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    // 댓글 생성
    public ReplyDto createReply(Long userId, CreateReplyRequest createReplyRequest) {
        Reply reply = Reply.builder()
                .postId(createReplyRequest.getPostId())
                .userId(userId)
                .createdAt(LocalDateTime.now())
                .content(createReplyRequest.getContent())
                .build();
        replyRepository.save(reply);
        return ReplyDto.fromEntity(reply);
    }

    // 전체 게시글 조회
    public List<Reply> getAllReply() {
        return replyRepository.findAll();
    }

    // 게시글 상세 조회
    public Reply getReplyById(Long id) {
        return replyRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
    }

    // 게시글 수정
    public ReplyDto updateReply(Long userId, Long id, UpdateReplyRequest updateReplyRequest) {
        Reply reply = getReplyById(id);
        reply.setContent(updateReplyRequest.getContent());
        replyRepository.save(reply);
        return ReplyDto.fromEntity(reply);
    }

    // 게시글 삭제
    public void deletePost(Long id) {
        replyRepository.deleteById(id);
    }


    // postid 로 댓글 조회
    public List<Reply> getRepliesByPostId(Long postId) {
        return replyRepository.findByPostId(postId);
    }


}

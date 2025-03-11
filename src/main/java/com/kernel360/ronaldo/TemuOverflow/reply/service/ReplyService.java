package com.kernel360.ronaldo.TemuOverflow.reply.service;

import com.kernel360.ronaldo.TemuOverflow.post.entity.Post;
import com.kernel360.ronaldo.TemuOverflow.reply.entity.Reply;
import com.kernel360.ronaldo.TemuOverflow.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    // 댓글 생성
    public Reply createReply(Reply reply) {
        return replyRepository.save(reply);
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
    public Reply updateReply(Long id, Reply updatedReply) {
        Reply reply = getReplyById(id);
        reply.setContent(updatedReply.getContent());
        return replyRepository.save(reply);
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

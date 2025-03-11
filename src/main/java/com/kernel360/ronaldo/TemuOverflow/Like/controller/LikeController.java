package com.kernel360.ronaldo.TemuOverflow.Like.controller;

import com.kernel360.ronaldo.TemuOverflow.Like.dto.LikeArticleResponse;
import com.kernel360.ronaldo.TemuOverflow.Like.dto.LikeReplyResponse;
import com.kernel360.ronaldo.TemuOverflow.Like.dto.LikeRequest;
import com.kernel360.ronaldo.TemuOverflow.Like.service.LikeService;
import com.kernel360.ronaldo.TemuOverflow.user.service.UserAuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class LikeController {
    private final UserAuthService userAuthService;
    private final LikeService likeService;

    @PostMapping("/article")
    public ResponseEntity<LikeArticleResponse> likeArticle(HttpServletRequest request, @RequestBody LikeRequest likeArticleRequest) {
        Long userId = userAuthService.getUserIdFromToken(request);
        return ResponseEntity.ok(likeService.likeArticle(userId, likeArticleRequest));
    }

    @PostMapping("/reply")
    public ResponseEntity<LikeReplyResponse> likeReply(HttpServletRequest request, @RequestBody LikeRequest likeReplyRequest) {
        Long userId = userAuthService.getUserIdFromToken(request);
        return ResponseEntity.ok(likeService.likeReply(userId, likeReplyRequest));
    }

}

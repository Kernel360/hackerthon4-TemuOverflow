package com.kernel360.ronaldo.TemuOverflow.chat.controller;

import com.kernel360.ronaldo.TemuOverflow.chat.dto.ChatRequest;
import com.kernel360.ronaldo.TemuOverflow.chat.dto.ChatResponse;
import com.kernel360.ronaldo.TemuOverflow.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public Mono<ResponseEntity<ChatResponse>> chat(@RequestBody ChatRequest request) {
        return chatService.processChat(request)
                .map(ResponseEntity::ok);
    }
}

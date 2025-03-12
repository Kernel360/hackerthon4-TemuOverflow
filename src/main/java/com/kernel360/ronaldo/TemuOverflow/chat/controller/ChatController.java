package com.kernel360.ronaldo.TemuOverflow.chat.controller;

import com.kernel360.ronaldo.TemuOverflow.chat.dto.ChatRequest;
import com.kernel360.ronaldo.TemuOverflow.chat.dto.ChatResponse;
import com.kernel360.ronaldo.TemuOverflow.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public Mono<ResponseEntity<ChatResponse>> chat(@RequestBody ChatRequest request) {
        log.info("AI api service in");
        return chatService.processChat(request)
                .map(ResponseEntity::ok);
    }
}

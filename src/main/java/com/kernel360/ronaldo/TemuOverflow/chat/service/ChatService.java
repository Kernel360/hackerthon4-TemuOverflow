package com.kernel360.ronaldo.TemuOverflow.chat.service;



import com.kernel360.ronaldo.TemuOverflow.chat.dto.ChatRequest;
import com.kernel360.ronaldo.TemuOverflow.chat.dto.ChatResponse;
import com.kernel360.ronaldo.TemuOverflow.chat.dto.ClaudeRequest;
import com.kernel360.ronaldo.TemuOverflow.chat.dto.ClaudeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Service
public class ChatService {

    private final WebClient webClient;

    @Value("${claude.api.model}")
    private String claudeModel;

    @Value("${claude.api.max-tokens}")
    private Integer maxTokens;

    // 고정 시스템 프롬프트 설정 - @Value 애노테이션 제거하고 직접 변수에 할당
    private final String prompt = "너는 코딩 에러 분석 전문가야. 사용자가 제공한 에러 메시지를 세심하게 분석하고, 가능한 원인과 단계별 해결 방법을 한국어로 300자 이내로 상세히 설명해줘.";

    public ChatService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<ChatResponse> processChat(ChatRequest chatRequest) {
        log.info("Processing chat request(info): {}", chatRequest.getMessage());
        log.debug("Processing chat request: {}", chatRequest.getMessage());

        ClaudeRequest claudeRequest = ClaudeRequest.createRequest(
                claudeModel,
                maxTokens,
                prompt,
                chatRequest.getMessage()
        );

        return webClient.post()
                .bodyValue(claudeRequest)
                .retrieve()
                .bodyToMono(ClaudeResponse.class)
                .timeout(Duration.ofSeconds(60))  // 타임아웃 명시적 설정
                .doOnError(WebClientResponseException.class, e -> {
                    log.error("Error calling Claude API: {}", e.getResponseBodyAsString(), e);
                })
                .doOnError(e -> {
                    log.error("Unexpected error: ", e);
                })
                .map(response -> {
                    log.debug("Received Claude response: {}", response);
                    return new ChatResponse(response.getTextResponse());
                })
                .onErrorResume(e -> {
                    String errorMessage = "Sorry, I couldn't process your request. " +
                            "Please try again later.";
                    return Mono.just(new ChatResponse(errorMessage));
                });
    }
}
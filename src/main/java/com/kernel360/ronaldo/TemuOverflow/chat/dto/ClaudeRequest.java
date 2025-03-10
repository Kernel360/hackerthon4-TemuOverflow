package com.kernel360.ronaldo.TemuOverflow.chat.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Data
@Builder
public class ClaudeRequest {
    private String model;
    private Integer max_tokens;
    private List<Map<String, Object>> messages;
    private String system;
    private Double temperature; // temperature 값 추가 (선택적)

    public static ClaudeRequest createRequest(String model, Integer maxTokens,String prompt, String userMessage) {
        List<Map<String, Object>> messagesList = new ArrayList<>();

        // 사용자 메시지 추가 (content 형식 수정)
        Map<String, Object> userMessageMap = Map.of(
                "role", "user",
                "content", List.of(
                        Map.of("type", "text", "text", userMessage)
                )
        );
        messagesList.add(userMessageMap);

        return ClaudeRequest.builder()
                .model(model)
                .max_tokens(maxTokens)
                .messages(messagesList)
                .temperature(0.7) // 기본 temperature 값 설정
                .system(prompt)
                .build();
    }
}
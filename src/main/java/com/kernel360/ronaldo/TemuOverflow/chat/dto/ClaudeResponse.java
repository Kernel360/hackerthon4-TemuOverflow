package com.kernel360.ronaldo.TemuOverflow.chat.dto;


import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class ClaudeResponse {
    private String id;
    private String model;
    private String role;
    private List<Map<String, Object>> content;
    private String stop_reason;
    private String stop_sequence;
    private Map<String, Integer> usage;

    public String getTextResponse() {
        if (content != null && !content.isEmpty()) {
            Map<String, Object> firstContent = content.get(0);
            if ("text".equals(firstContent.get("type"))) {
                return (String) firstContent.get("text");
            }
        }
        return "No response text available";
    }
}
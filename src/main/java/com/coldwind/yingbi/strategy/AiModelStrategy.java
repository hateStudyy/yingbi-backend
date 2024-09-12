package com.coldwind.yingbi.strategy;

public interface AiModelStrategy {
    String doChat(Long modelId, String message);
}
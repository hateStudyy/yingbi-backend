package com.coldwind.yingbi.config;

import com.coldwind.yingbi.strategy.AiModelStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class AiStrategyConfig {

    @Bean
    public Map<String, AiModelStrategy> aiModelStrategies(List<AiModelStrategy> strategies) {
        return strategies.stream()
                         .collect(Collectors.toMap(strategy -> strategy.getClass().getSimpleName().replace("Strategy", "").toLowerCase(),
                                                   Function.identity()));
    }
}
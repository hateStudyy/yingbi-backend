package com.coldwind.yingbi.manager;

import com.coldwind.yingbi.common.ErrorCode;
import com.coldwind.yingbi.exception.BusinessException;
import com.coldwind.yingbi.strategy.AiModelStrategy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author ckl
 * @since 2023/7/20 14:01
 */
@Service
public class AiManager {

    @Resource
    private Map<String, AiModelStrategy> aiModelStrategies; // 依赖注入所有策略实现

    /**
     * Ai对话
     * @param modelId
     * @param message
     * @return
     */
    public String doChart(String modelType, Long modelId, String message) {
        AiModelStrategy strategy = aiModelStrategies.get(modelType);
        if (strategy == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "不支持的 AI 模型");
        }
        return strategy.doChat(modelId, message);
    }
}

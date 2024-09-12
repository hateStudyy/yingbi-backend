package com.coldwind.yingbi.strategy;

import com.coldwind.yingbi.common.ErrorCode;
import com.coldwind.yingbi.exception.BusinessException;
import com.yupi.yucongming.dev.client.YuCongMingClient;
import com.yupi.yucongming.dev.common.BaseResponse;
import com.yupi.yucongming.dev.model.DevChatRequest;
import com.yupi.yucongming.dev.model.DevChatResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class YuCongMingStrategy implements AiModelStrategy {

    @Resource
    private YuCongMingClient yuCongMingClient;

    @Override
    public String doChat(Long modelId, String message) {
        //构造请求参数
        DevChatRequest devChatRequest = new DevChatRequest();
        devChatRequest.setModelId(modelId);
        devChatRequest.setMessage(message);
        BaseResponse<DevChatResponse> chat = yuCongMingClient.doChat(devChatRequest);
        if (chat == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "输入消息为空，请联系管理员");
        }
        return chat.getData().getContent();
    }
}
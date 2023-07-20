package com.yupi.springbootinit.manager;

import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.yucongming.dev.client.YuCongMingClient;
import com.yupi.yucongming.dev.common.BaseResponse;
import com.yupi.yucongming.dev.model.DevChatRequest;
import com.yupi.yucongming.dev.model.DevChatResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ckl
 * @since 2023/7/20 14:01
 */
@Service
public class AiManager {

    @Resource
    private YuCongMingClient yuCongMingClient;

    /**
     * Ai对话
     * @param modelId
     * @param message
     * @return
     */
    public String doChart(Long modelId,String message) {

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

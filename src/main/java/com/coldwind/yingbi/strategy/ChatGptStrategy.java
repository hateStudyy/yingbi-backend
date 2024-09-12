package com.coldwind.yingbi.strategy;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ChatGptStrategy implements AiModelStrategy {

    private static final String SYSTEM_CONTENT = "你是一个数据分析师和前端开发专家，接下来我会按照以下固定格式给你提供内容：\n" +
            "分析需求：\n" +
            "{数据分析的需求或者目标}\n" +
            "原始数据：\n" +
            "{csv格式的原始数据, 用,作为分隔}\n" +
            "请根据这两部分内容，按照以下指定格式生成内容（此外不要输出任何多余的开头、结尾、注释）\n" +
            "【【【【【\n" +
            "{前端Echarts V5的option配置对象js代码，注意这里是json字符串形式，合理地将数据进行可视化，不要生成任何多余的内容，比如注释}\n" +
            "【【【【【\n" +
            "{明确的数据分析结论、越详细越好，不要生成多余的注释}";

    @Value("${openai.api-key}")
    private String apiKey;

    @Override
    public String doChat(Long modelId, String message) {
        // 设置 HTTP 代理
        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "58309");

        // 设置 HTTPS 代理
        System.setProperty("https.proxyHost", "127.0.0.1");
        System.setProperty("https.proxyPort", "58309");

        OpenAiService service = new OpenAiService(apiKey);

        // 创建 ChatCompletionRequest
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(Arrays.asList(
                        new ChatMessage("system", SYSTEM_CONTENT),  // 系统消息设定角色
                        new ChatMessage("user", message)
                ))
                .build();

        String aiResponse = null;
        // 发送请求并获取响应
        try {
            aiResponse = service.createChatCompletion(chatCompletionRequest)
                    .getChoices()
                    .stream()
                    .findFirst()  // 获取第一个选择
                    .map(choice -> choice.getMessage().getContent())  // 提取内容
                    .orElse("No response");  // 如果没有响应，返回默认值
        } catch (Exception e) {
            System.err.println("请求失败: " + e.getMessage());
            e.printStackTrace();
        }
        return aiResponse;
    }
}
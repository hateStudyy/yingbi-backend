package com.coldwind.yingbi.bizmq;

import com.coldwind.yingbi.common.ErrorCode;
import com.coldwind.yingbi.constant.CommonConstant;
import com.coldwind.yingbi.exception.BusinessException;
import com.coldwind.yingbi.manager.AiManager;
import com.coldwind.yingbi.service.ChartService;
import com.rabbitmq.client.Channel;
import com.coldwind.yingbi.model.entity.Chart;
import com.coldwind.yingbi.model.enums.ChartStatusEnum;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author ckl
 * @since 2023/7/26 9:21
 */
@Component
@Slf4j
public class BiMessageConsumer {

    @Resource
    private ChartService chartService;

    @Resource
    private AiManager aiManager;

    // 指定程序监听的消息队列和确认机制
    @SneakyThrows
    @RabbitListener(queues = {BiMqConstant.BI_QUEUE_NAME}, ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        log.info("receiveMessage message = {}", message);
        if(StringUtils.isBlank(message)) {
            // 消息拒绝
            channel.basicNack(deliveryTag, false, false);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"消息为空");
        }

        long chartId = Long.parseLong(message);
        Chart chart = chartService.getById(chartId);
        if(chart == null) {
            channel.basicNack(deliveryTag, false, false);
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"图标为空");
        }

        Chart updateChart = new Chart();
        updateChart.setId(chart.getId());
        updateChart.setStatus(ChartStatusEnum.RUNNING.getStatus());
        boolean b = chartService.updateById(updateChart);
        if (!b) {
            channel.basicNack(deliveryTag, false, false);
            handleChartUpdateError(chart.getId(), "图表状态更改失败");
            return;
        }
        // 调用 ai
        String doChart = aiManager.doChart(CommonConstant.BI_MODEL_ID ,buildUserInput(chart));
        String[] splits = doChart.split("【【【【【");

        if (splits.length < 3) {
            channel.basicNack(deliveryTag, false, false);
            handleChartUpdateError(chart.getId(), "AI 生成错误");
            return;
        }
        String genChart = splits[1].trim();
        String genResult = splits[2].trim();

        // 更新成功 修改数据库状态
        Chart updateChartResuslt = new Chart();
        updateChartResuslt.setId(chart.getId());
        updateChartResuslt.setStatus(ChartStatusEnum.SUCCEED.getStatus());
        updateChartResuslt.setGenChart(genChart);
        updateChartResuslt.setGenResult(genResult);
        boolean result = chartService.updateById(updateChartResuslt);
        if (!result) {
            channel.basicNack(deliveryTag, false, false);
            handleChartUpdateError(chart.getId(), "图表状态更改失败");
        }

        // 消息确认
        channel.basicAck(deliveryTag,false);
    }

    /**
     * 构造用户输入
     * @param chart
     * @return
     */
    private String buildUserInput(Chart chart) {

        String goal = chart.getGoal();
        String chartType = chart.getChartType();
        String csvData = chart.getChartDate();
        // 构造用户输入
        StringBuilder userInput = new StringBuilder();
        userInput.append("分析需求：").append("\n");
        // 拼接 图表类型
        String userGoal = goal;
        if (StringUtils.isNotBlank(userGoal)) {
            userGoal += "，请使用" + chartType;
        }
        userInput.append(userGoal).append("\n");
        // 拼接原始数据
        userInput.append("原始数据：").append("\n");

        userInput.append(csvData).append("\n");

        return new String(userInput);
    }

    private void handleChartUpdateError(long chartId, String execMessage) {
        Chart updateChartResuslt = new Chart();
        updateChartResuslt.setId(chartId);
        updateChartResuslt.setStatus(ChartStatusEnum.FAILED.getStatus());
        updateChartResuslt.setExecMessage("execMessage");
        boolean result = chartService.updateById(updateChartResuslt);
        if (!result) {
            log.error("更新图表失败状态失败" + chartId + "," + execMessage);
        }
    }
}

package com.coldwind.yingbi.bizmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 创建交换机队列，并绑定 (只用启动前执行一次)
 *
 * @author ckl
 * @since 2023/7/26 9:45
 */
public class MqInitMain {

    public static void main(String[] args) {


        Connection connection = null;
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            connection = factory.newConnection();
            Channel channel = connection.createChannel();

            String EXCHANGE_NAME =BiMqConstant.BI_EXCHANGE_NAME;
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            // 随机分配一个名称
            String queueName = BiMqConstant.BI_QUEUE_NAME;
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, EXCHANGE_NAME, BiMqConstant.BI_QUEUE_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

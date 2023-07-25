package com.yupi.springbootinit.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.util.HashMap;
import java.util.Map;

public class DlxDirectConsumer {
    private static final String DEAD_EXCHANGE_NAME = "dlx-direct-exchange";
    private static final String EXCHANGE_NAME = "direct2-exchange";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");


        // 指定私信队列参数要绑定到哪个交换机
        Map<String, Object> args1 = new HashMap<String, Object>();
        args1.put("x-dead-letter-exchange", DEAD_EXCHANGE_NAME);
        args1.put("x-dead-letter-routing-key", "老板");
        // 随机分配一个名称
        String queueName = "小猫的工作队列";
        channel.queueDeclare(queueName, true, false, false, args1);
        channel.queueBind(queueName, EXCHANGE_NAME, "小猫");

        Map<String, Object> args2 = new HashMap<String, Object>();
        args2.put("x-dead-letter-exchange", DEAD_EXCHANGE_NAME);
        args2.put("x-dead-letter-routing-key", "外包");

        // 随机分配一个名称
        String queueName2 = "小狗的工作队列";
        channel.queueDeclare(queueName2, true, false, false, args2);
        channel.queueBind(queueName2, EXCHANGE_NAME, "小狗");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback1 = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            // 拒绝消息
            channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, false);
            System.out.println(" [小猫] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };
        DeliverCallback deliverCallback2 = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            // 拒绝消息
            channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, false);
            System.out.println(" [小狗] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };
        channel.basicConsume(queueName, false, deliverCallback1, consumerTag -> {
        });
        channel.basicConsume(queueName2, false, deliverCallback2, consumerTag -> {
        });


    }
}
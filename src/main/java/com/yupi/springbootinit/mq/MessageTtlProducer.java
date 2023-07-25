package com.yupi.springbootinit.mq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MessageTtlProducer {
    private final static String QUEUE_NAME = "message_ttl_queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        // 建立连接
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // 发送消息
            String message = "Hello World!";
            byte[] messageBodyBytes = "Hello, world!".getBytes();
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .expiration("5000")
                    .build();
            channel.basicPublish("", QUEUE_NAME, properties, messageBodyBytes);
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
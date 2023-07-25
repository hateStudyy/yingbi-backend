package com.yupi.springbootinit.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.util.Scanner;

public class DxlDirectProducer {

  private static final String DEAD_EXCHANGE_NAME = "dlx-direct-exchange";

    private static final String WORK_EXCHANGE_NAME = "direct2-exchange";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    try (Connection connection = factory.newConnection();
         Channel channel = connection.createChannel()) {
        // 声明死信交换机
        channel.exchangeDeclare(DEAD_EXCHANGE_NAME, "direct");

        // 随机分配一个名称
        String queueName1 = "老板的死信队列";
        channel.queueDeclare(queueName1, true, false, false, null);
        channel.queueBind(queueName1, DEAD_EXCHANGE_NAME, "老板");
        // 随机分配一个名称
        String queueName2 = "外包的死信队列";
        channel.queueDeclare(queueName2, true, false, false, null);
        channel.queueBind(queueName2, DEAD_EXCHANGE_NAME, "外包");


        // 死信队列处理
        DeliverCallback bossDeliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [老板] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };
        DeliverCallback outsourcingDeliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [外包] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };
        channel.basicConsume(queueName1, false, bossDeliverCallback, consumerTag -> {
        });
        channel.basicConsume(queueName2, false, outsourcingDeliverCallback, consumerTag -> {
        });

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String userInput = scanner.nextLine();
            String[] strings = userInput.split(" ");
            if(strings.length <= 1) {
                continue;
            }
            String message = strings[0];
            String routingKey = strings[1];

            channel.basicPublish(WORK_EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + " with routing:" + routingKey+ "'");
        }
    }
  }
  //..
}
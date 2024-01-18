package com.coldwind.yingbi.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class TopicConsumer {

  private static final String EXCHANGE_NAME = "topic_exchange";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.exchangeDeclare(EXCHANGE_NAME, "topic");

      // 随机分配一个名称
      String queueName = "前端工作队列";
      channel.queueDeclare(queueName, true, false, false, null);
      channel.queueBind(queueName, EXCHANGE_NAME, "#.前端.#");

      // 随机分配一个名称
      String queueName2 = "后端工作队列";
      channel.queueDeclare(queueName2, true, false, false, null);
      channel.queueBind(queueName2, EXCHANGE_NAME, "#.后端.#");
      // 随机分配一个名称
      String queueName3 = "产品工作队列";
      channel.queueDeclare(queueName3, true, false, false, null);
      channel.queueBind(queueName3, EXCHANGE_NAME, "#.产品.#");

      System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

      // 定义队列执行函数
      DeliverCallback xiaoadeliverCallback = (consumerTag, delivery) -> {
          String message = new String(delivery.getBody(), "UTF-8");
          System.out.println(" [xiaoa] Received '" +
                  delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
      };
      DeliverCallback xiaobdeliverCallback2 = (consumerTag, delivery) -> {
          String message = new String(delivery.getBody(), "UTF-8");
          System.out.println(" [xiaob] Received '" +
                  delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
      };
      DeliverCallback xiaocdeliverCallback2 = (consumerTag, delivery) -> {
          String message = new String(delivery.getBody(), "UTF-8");
          System.out.println(" [xiaoc] Received '" +
                  delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
      };
      DeliverCallback xiaoddeliverCallback2 = (consumerTag, delivery) -> {
          String message = new String(delivery.getBody(), "UTF-8");
          System.out.println(" [xiaod] Received '" +
                  delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
      };
      // xiaoa,xiaob 监听前端队列
      channel.basicConsume(queueName, true, xiaoadeliverCallback, consumerTag -> {
      });
      channel.basicConsume(queueName, true, xiaobdeliverCallback2, consumerTag -> {
      });
      channel.basicConsume(queueName2, true, xiaocdeliverCallback2, consumerTag -> {
      });
      channel.basicConsume(queueName3, true, xiaoddeliverCallback2, consumerTag -> {
      });
  }
}
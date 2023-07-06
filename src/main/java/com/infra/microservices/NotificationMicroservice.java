package com.infra.microservices;

import com.rabbitmq.client.*;
import java.nio.charset.StandardCharsets;

public class NotificationMicroservice {
    public static void main(String[] args) throws Exception {
        System.out.println("[*] Waiting for messages. To exit press CTRL+C [*]");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String exchangeName = "company_exchange";
        String queueName = "company_notifications_queue";
        String routingKey = "new_company";

        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
        channel.queueBind(queueName, exchangeName, routingKey);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println(message + " was registered successfully");
            }
        };

        channel.basicConsume(queueName, true, consumer);
    }
}
package com.example.demo.listener;

import com.example.demo.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQReceiver {

    // Слушаем сообщения из нашей очереди
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(String message) {
        System.out.println(" [x] Received '" + message + "' from RabbitMQ");
        // Здесь может быть логика обработки задачи
    }
}

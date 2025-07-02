package com.example.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    // Имя нашей очереди.Это место, куда будут складываться письма по задачам, ожидающие обработки.
    public static final String QUEUE_NAME = "my_task_queue";
    // Имя обмена (exchange). Все входящие письма по задачам сначала попадают сюда
    public static final String EXCHANGE_NAME = "task_exchange";
    // Ключ маршрутизации
    public static final String ROUTING_KEY = "task.new";

    // 1. Создаем бин очереди (Queue)
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false); // 'false' означает, что очередь не является durable (не переживет перезапуск брокера)
    }

    // 2. Создаем бин обмена (TopicExchange)
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME); // Ваш "task_exchange" — это Topic Exchange
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        // Эта привязка говорит: "Всё, что имеет точный ключ 'task.new', отправляй в 'my_task_queue'"
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY); // ROUTING_KEY = "task.new"
    }
}


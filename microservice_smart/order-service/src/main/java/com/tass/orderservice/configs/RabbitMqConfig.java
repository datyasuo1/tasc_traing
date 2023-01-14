package com.tass.orderservice.configs;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Value("${spring.rabbitmq.exchange}")
    private String EXCHANGE_NAME;

    @Value("${spring.rabbitmq.queue.order}")
    private String QUEUE_NAME;

    @Value("${spring.rabbitmq.routing-key.order}")
    private String ROUTING_KEY_NAME;

    @Value("${spring.rabbitmq.queue.product}")
    private String QUEUE_NAME_PRODUCT;

    @Value("${spring.rabbitmq.routing-key.product}")
    private String ROUTING_KEY_PRODUCT;

    @Bean
    TopicExchange exchange(){
        return new TopicExchange(EXCHANGE_NAME);
    }
    @Bean
    Queue queueShoppingCart(){
        return new Queue(QUEUE_NAME,false);
    }
    @Bean
    Binding shoppingCartBinding(Queue queueShoppingCart,TopicExchange exchange){
        return BindingBuilder.bind(queueShoppingCart).to(exchange).with(ROUTING_KEY_NAME);
    }

    @Bean
    Queue queueProduct(){
        return new Queue(QUEUE_NAME_PRODUCT,false);
    }
    @Bean
    Binding productBinding(Queue queueProduct,TopicExchange exchange){
        return BindingBuilder.bind(queueProduct).to(exchange).with(ROUTING_KEY_NAME);
    }
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}

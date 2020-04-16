package com.invillia.habbitmqpoc.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${constant.rabbitmq.queue}")
    private String queueName;

    @Value("${constant.rabbitmq.exchange}")
    private String exchangeName;

    @Value("${constant.rabbitmq.routingkey}")
    private String routingkey;

    @Value("${constant.rabbitmq.queueDLQ}")
    private String DLQName;

    @Value("${constant.rabbitmq.exchangeDLQ}")
    private String exchangeDLQName;

    @Value("${constant.rabbitmq.routingkeyDLQ}")
    private String routingkeyDLQ;


    // Definindo a exchange da DLQ
    @Bean
    DirectExchange deadLetterExchange() {
        return new DirectExchange(exchangeDLQName);
    }

    // Definindo uma DLQ
    @Bean
    Queue dlq() {
        return new Queue(DLQName, true);
    }


    @Bean
    Binding DLQbinding() {
        return BindingBuilder.bind(dlq()).to(deadLetterExchange()).with(routingkeyDLQ);
    }


    // Definindo uma exchange
    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchangeName);
    }


    // Definindo uma queue. Em caso de problemas no cosumo da mensagem, ela sera enviada para a DLQ.
    @Bean
    Queue queue() {
        return QueueBuilder.durable(queueName)
                .withArgument("x-dead-letter-exchange", exchangeDLQName)
                .withArgument("x-dead-letter-routing-key", DLQName)
                .build();
    }


    // Criando um binding para vincular a Queue a Exchange
    @Bean
    Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(routingkey);
    }


    // Serializando da mensagem Json para entidade Java
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}

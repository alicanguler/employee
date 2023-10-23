package com.task.employee.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitMqConfig {
    @Value("${queue.routingkey.created}")
    private String createdRoutingKey;

    @Value("${queue.routingkey.created}")
    private String updatedRoutingKey;

    @Value("${queue.routingkey.created}")
    private String deletedRoutingKey;

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    public Queue employeeCreatedQueue(){
        return new Queue("employee-created-queue");
    }

    @Bean
    public DirectExchange employeeCreatedExchange() {
        return new DirectExchange("employee.created");
    }
    @Bean
    public Binding bindingCreated(Queue employeeCreatedQueue, DirectExchange employeeCreatedExchange) {
        return BindingBuilder.bind(employeeCreatedQueue).to(employeeCreatedExchange).with(createdRoutingKey);
    }

    @Bean
    public Queue employeeUpdatedQueue(){
        return new Queue("employee-updated-queue");
    }

    @Bean
    public DirectExchange employeeUpdatedExchange() {
        return new DirectExchange("employee.updated");
    }
    @Bean
    public Binding bindingUpdated(Queue employeeUpdatedQueue, DirectExchange employeeUpdatedExchange) {
        return BindingBuilder.bind(employeeUpdatedQueue).to(employeeUpdatedExchange).with(updatedRoutingKey);
    }

    @Bean
    public Queue employeeDeletedQueue(){
        return new Queue("employee-deleted-queue");
    }
    @Bean
    public DirectExchange employeeDeletedExchange() {
        return new DirectExchange("employee.deleted");
    }
    @Bean
    public Binding bindingDeleted(Queue employeeDeletedQueue, DirectExchange employeeDeletedExchange) {
        return BindingBuilder.bind(employeeDeletedQueue).to(employeeDeletedExchange).with(deletedRoutingKey);
    }


}

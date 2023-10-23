package com.task.employee.sender;

import com.task.employee.event.EmployeeEvent;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class RabbitMQSender {
    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange createdExchange;
    private final DirectExchange deletedExchange;
    private final DirectExchange updatedExchange;

    @Value("${queue.routingkey.created}")
    private String createdRoutingKey;

    @Value("${queue.routingkey.created}")
    private String updatedRoutingKey;

    @Value("${queue.routingkey.created}")
    private String deletedRoutingKey;

    public RabbitMQSender(RabbitTemplate rabbitTemplate,
                          @Qualifier("employeeCreatedExchange") DirectExchange createdExchange,
                          @Qualifier("employeeDeletedExchange") DirectExchange deletedExchange,
                          @Qualifier("employeeUpdatedExchange") DirectExchange updatedExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.createdExchange = createdExchange;
        this.deletedExchange = deletedExchange;
        this.updatedExchange = updatedExchange;
    }


    public void sendEmployeeCreatedEvent(EmployeeEvent event) {
        rabbitTemplate.convertAndSend(createdExchange.getName(), createdRoutingKey, event);
    }

    public void sendEmployeeDeletedEvent(EmployeeEvent event) {
        rabbitTemplate.convertAndSend(deletedExchange.getName(), deletedRoutingKey, event);
    }

    public void sendEmployeeUpdatedEvent(EmployeeEvent event) {
        rabbitTemplate.convertAndSend(updatedExchange.getName(), updatedRoutingKey, event);
    }

}

package com.invillia.habbitmqpoc.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invillia.habbitmqpoc.model.Employee;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQReceiver {

    @Autowired
    private AmqpTemplate rabbitTemplate;


    @Value("${constant.rabbitmq.queue}")
    private String queueName;

    public Employee receive() {
        Employee employee = new Employee();

        try {
            String in = new String(rabbitTemplate.receive(queueName).getBody());
            employee = new ObjectMapper().readValue(in, Employee.class);
            return employee;

        } catch (Exception e) {
            System.out.println(e);
        }
        return employee;
    }
}

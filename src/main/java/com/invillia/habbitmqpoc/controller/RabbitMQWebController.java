package com.invillia.habbitmqpoc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.invillia.habbitmqpoc.model.Employee;
import com.invillia.habbitmqpoc.service.RabbitMQReceiver;
import com.invillia.habbitmqpoc.service.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RabbitMQWebController {

    @Autowired
    RabbitMQReceiver rabbitMQReceiver;

    @Autowired
    RabbitMQSender rabbitMQSender;

    @GetMapping(value = "/publisher")
    public String producer(@RequestParam("name") String name, @RequestParam("id") Long id) {

        Employee employee = new Employee();
        employee.setId(id);
        employee.setName(name);
        rabbitMQSender.send(employee);

        return "Message sent to the RabbitMQ Successfully";
    }


    @GetMapping(value = "/consumer")
    public String receiver() throws JsonProcessingException {

        final Employee employee = rabbitMQReceiver.receive();

        return "Message received from RabbitMQ Successfully: " + employee.toString();
    }
}
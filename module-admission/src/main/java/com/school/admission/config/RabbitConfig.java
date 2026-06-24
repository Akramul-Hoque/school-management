package com.school.admission.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String ADMISSION_EXCHANGE = "admission.exchange";
    public static final String ADMISSION_APPROVED_ROUTING_KEY = "admission.approved";
    public static final String ADMISSION_APPROVED_QUEUE = "admission-approved-queue";

    @Bean
    public Queue admissionApprovedQueue() {
        return new Queue(ADMISSION_APPROVED_QUEUE, true);
    }

    @Bean
    public TopicExchange admissionExchange() {
        return new TopicExchange(ADMISSION_EXCHANGE);
    }

    @Bean
    public Binding bindingAdmissionApproved(Queue admissionApprovedQueue, TopicExchange admissionExchange) {
        return BindingBuilder.bind(admissionApprovedQueue).to(admissionExchange).with(ADMISSION_APPROVED_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

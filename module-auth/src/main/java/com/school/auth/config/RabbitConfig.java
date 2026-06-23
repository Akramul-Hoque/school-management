package com.school.auth.config;

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

    public static final String STAFF_ONBOARDED_QUEUE = "auth-staff-onboarded-queue";
    public static final String STUDENT_CREATED_QUEUE = "auth-student-created-queue";
    public static final String HR_EXCHANGE = "hr.exchange";
    public static final String STUDENT_EXCHANGE = "student.exchange";
    public static final String STAFF_ONBOARDED_ROUTING_KEY = "staff.onboarded";
    public static final String STUDENT_CREATED_ROUTING_KEY = "student.created";

    @Bean
    public Queue staffOnboardedQueue() {
        return new Queue(STAFF_ONBOARDED_QUEUE, true);
    }

    @Bean
    public Queue studentCreatedQueue() {
        return new Queue(STUDENT_CREATED_QUEUE, true);
    }

    @Bean
    public TopicExchange hrExchange() {
        return new TopicExchange(HR_EXCHANGE);
    }

    @Bean
    public TopicExchange studentExchange() {
        return new TopicExchange(STUDENT_EXCHANGE);
    }

    @Bean
    public Binding bindingStaffOnboarded(Queue staffOnboardedQueue, TopicExchange hrExchange) {
        return BindingBuilder.bind(staffOnboardedQueue).to(hrExchange).with(STAFF_ONBOARDED_ROUTING_KEY);
    }

    @Bean
    public Binding bindingStudentCreated(Queue studentCreatedQueue, TopicExchange studentExchange) {
        return BindingBuilder.bind(studentCreatedQueue).to(studentExchange).with(STUDENT_CREATED_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

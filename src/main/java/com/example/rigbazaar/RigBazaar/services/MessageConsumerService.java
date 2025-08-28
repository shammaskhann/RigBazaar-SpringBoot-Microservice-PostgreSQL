package com.example.rigbazaar.RigBazaar.services;

import com.example.rigbazaar.RigBazaar.config.RabbitMQConfig;
import com.example.rigbazaar.RigBazaar.entities.wrapper.MessageWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
class MessageConsumerService {

    private final MessageService messageService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void consume(MessageWrapper wrapper) {
        try {
            log.info("Processing message for room {}", wrapper.getRoomId());
            messageService.processMessage(wrapper.getMessageRequest(), wrapper.getRoomId());
        } catch (Exception e) {
            log.error("Failed to process message for room {}: {}",
                    wrapper.getRoomId(), e.getMessage());
            // Here you could implement retry logic or dead-letter queue handling
        }
    }
}
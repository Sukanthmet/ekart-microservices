package com.ekart.orderservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.ekart.common.event.OrderEvent;
@Service

public class OrderProducer {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderEvent(OrderEvent event) {
        kafkaTemplate.send("order-topic", event);
    }

    public void sendOrderCancelEvent(OrderEvent event) {
        kafkaTemplate.send("order-cancel-topic", event);
    }
    public void sendOrderUpdateEvent(OrderEvent event) {
        kafkaTemplate.send("order-update-topic", event);
    }

}





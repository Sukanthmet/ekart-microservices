package com.ekart.notificationservice.kafka;

import com.ekart.notificationservice.kafka.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    @KafkaListener(topics = "order-topic", groupId = "notification-group-v10")
    public void consume(OrderEvent event) {
        System.out.println("Order received: " + event.getOrderId());
    }
}
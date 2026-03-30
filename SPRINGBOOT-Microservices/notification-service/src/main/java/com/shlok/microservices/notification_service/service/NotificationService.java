package com.shlok.microservices.notification_service.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.shlok.microservices.notification_service.event.OrderPlacedMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final JavaMailSender javaMailSender;

    public NotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @KafkaListener(topics = "order-placed")
    public void listner(OrderPlacedMessage orderPlacedMessage){

            log.info("Got message from order-placed topic: {}", orderPlacedMessage); 
            if (orderPlacedMessage == null || orderPlacedMessage.getEmail() == null || orderPlacedMessage.getEmail().isBlank()) {
                log.warn("Skipping notification because email is missing in message: {}", orderPlacedMessage);
                return;
            }

            MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("springshop@email.com");
            messageHelper.setTo(orderPlacedMessage.getEmail());
            messageHelper.setSubject(String.format("Your Order with OrderNumber %s is placed successfully", orderPlacedMessage.getOrderNumber()));
            messageHelper.setText(String.format("""
                            Hi %s,

                            Your order with order number %s is now placed successfully.
                            
                            Best Regards
                            Spring Shop
                            """,
                    orderPlacedMessage.getFirstName() != null ? orderPlacedMessage.getFirstName() : "Customer",
                    orderPlacedMessage.getOrderNumber()));
        };
        try {
            javaMailSender.send(messagePreparator);
            log.info("Order Notifcation email sent!!");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new RuntimeException("Exception occurred when sending mail to " + orderPlacedMessage.getEmail(), e);
        }
    }
}

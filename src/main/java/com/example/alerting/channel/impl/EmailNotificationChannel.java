package com.example.alerting.channel.impl;

import com.example.alerting.channel.NotificationChannel;
import com.example.alerting.model.ChannelType;
import com.example.alerting.model.DeliveryResult;
import com.example.alerting.model.NotificationPayload;
import com.example.alerting.model.Recipient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class EmailNotificationChannel implements NotificationChannel {

    @Override
    public ChannelType getType() {
        return ChannelType.EMAIL;
    }

    @Override
    @Async
    public CompletableFuture<DeliveryResult> send(Recipient recipient, NotificationPayload payload) {
        try {
            System.out.printf("[EMAIL DISPATCH] Sending to %s: %s%n", recipient.destination(), payload.title());
            Thread.sleep(100);
            return CompletableFuture.completedFuture(DeliveryResult.success(getType()));
        } catch (Exception e) {
            return CompletableFuture.completedFuture(
                DeliveryResult.failure(getType(), "Failed to send Email: " + e.getMessage())
            );
        }
    }
}

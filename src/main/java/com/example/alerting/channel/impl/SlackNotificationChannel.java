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
public class SlackNotificationChannel implements NotificationChannel {

    @Override
    public ChannelType getType() {
        return ChannelType.SLACK;
    }

    @Override
    @Async
    public CompletableFuture<DeliveryResult> send(Recipient recipient, NotificationPayload payload) {
        try {
            System.out.printf("[SLACK DISPATCH] Webhook %s: [%s] %s%n", 
                recipient.destination(), payload.severity(), payload.title());
            Thread.sleep(150);
            return CompletableFuture.completedFuture(DeliveryResult.success(getType()));
        } catch (Exception e) {
            return CompletableFuture.completedFuture(
                DeliveryResult.failure(getType(), "Failed to send Slack webhook: " + e.getMessage())
            );
        }
    }
}

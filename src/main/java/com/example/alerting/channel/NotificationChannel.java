package com.example.alerting.channel;

import com.example.alerting.model.ChannelType;
import com.example.alerting.model.DeliveryResult;
import com.example.alerting.model.NotificationPayload;
import com.example.alerting.model.Recipient;

import java.util.concurrent.CompletableFuture;

public interface NotificationChannel {
    ChannelType getType();
    CompletableFuture<DeliveryResult> send(Recipient recipient, NotificationPayload payload);
}

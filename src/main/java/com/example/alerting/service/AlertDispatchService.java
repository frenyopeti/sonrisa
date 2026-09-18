package com.example.alerting.service;

import com.example.alerting.channel.NotificationChannel;
import com.example.alerting.channel.NotificationChannelRegistry;
import com.example.alerting.entity.AlertRule;
import com.example.alerting.entity.DeliveryLog;
import com.example.alerting.model.ChannelType;
import com.example.alerting.model.NotificationPayload;
import com.example.alerting.model.Recipient;
import com.example.alerting.repository.DeliveryLogRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AlertDispatchService {

    private final NotificationChannelRegistry channelRegistry;
    private final DeliveryLogRepository deliveryLogRepository;

    public AlertDispatchService(NotificationChannelRegistry channelRegistry,
                                DeliveryLogRepository deliveryLogRepository) {
        this.channelRegistry = channelRegistry;
        this.deliveryLogRepository = deliveryLogRepository;
    }

    public void dispatchToRule(UUID eventId, AlertRule rule, NotificationPayload payload) {
        Recipient recipient = new Recipient(rule.getUserId(), rule.getRecipientDestination());

        for (ChannelType channelType : rule.getChannels()) {
            NotificationChannel channel = channelRegistry.getChannel(channelType)
                    .orElse(null);

            if (channel == null) {
                deliveryLogRepository.save(new DeliveryLog(
                    eventId, rule.getId(), channelType, 
                    com.example.alerting.model.DeliveryStatus.FAILED, 
                    "Channel provider bean not registered"
                ));
                continue;
            }

            channel.send(recipient, payload).thenAccept(result -> {
                deliveryLogRepository.save(new DeliveryLog(
                    eventId, rule.getId(), channelType, 
                    result.status(), result.errorMessage()
                ));
            });
        }
    }
}

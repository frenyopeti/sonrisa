package com.example.alerting.entity;

import com.example.alerting.model.ChannelType;
import com.example.alerting.model.DeliveryStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "delivery_logs")
public class DeliveryLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID eventId;
    private UUID ruleId;

    @Enumerated(EnumType.STRING)
    private ChannelType channel;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    private String errorMessage;
    private Instant dispatchedAt;

    public DeliveryLog() {}

    public DeliveryLog(UUID eventId, UUID ruleId, ChannelType channel, DeliveryStatus status, String errorMessage) {
        this.eventId = eventId;
        this.ruleId = ruleId;
        this.channel = channel;
        this.status = status;
        this.errorMessage = errorMessage;
        this.dispatchedAt = Instant.now();
    }

    public UUID getId() { return id; }
    public UUID getEventId() { return eventId; }
    public UUID getRuleId() { return ruleId; }
    public ChannelType getChannel() { return channel; }
    public DeliveryStatus getStatus() { return status; }
    public String getErrorMessage() { return errorMessage; }
    public Instant getDispatchedAt() { return dispatchedAt; }
}

package com.example.alerting.entity;

import com.example.alerting.model.ChannelType;
import com.example.alerting.model.EventCategory;
import com.example.alerting.model.SeverityLevel;
import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "alert_rules")
public class AlertRule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String userId;

    @Enumerated(EnumType.STRING)
    private EventCategory category;

    @Enumerated(EnumType.STRING)
    private SeverityLevel minSeverity;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<ChannelType> channels;

    private String recipientDestination;

    private boolean active = true;

    public AlertRule() {}

    public AlertRule(String userId, EventCategory category, SeverityLevel minSeverity, Set<ChannelType> channels, String recipientDestination) {
        this.userId = userId;
        this.category = category;
        this.minSeverity = minSeverity;
        this.channels = channels;
        this.recipientDestination = recipientDestination;
    }

    public UUID getId() { return id; }
    public String getUserId() { return userId; }
    public EventCategory getCategory() { return category; }
    public SeverityLevel getMinSeverity() { return minSeverity; }
    public Set<ChannelType> getChannels() { return channels; }
    public String getRecipientDestination() { return recipientDestination; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}

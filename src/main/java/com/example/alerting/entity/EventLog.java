package com.example.alerting.entity;

import com.example.alerting.model.EventCategory;
import com.example.alerting.model.SeverityLevel;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "event_logs")
public class EventLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private EventCategory category;

    @Enumerated(EnumType.STRING)
    private SeverityLevel severity;

    private String title;

    @Column(length = 1000)
    private String summary;

    private Instant occurredAt;

    public EventLog() {}

    public EventLog(EventCategory category, SeverityLevel severity, String title, String summary, Instant occurredAt) {
        this.category = category;
        this.severity = severity;
        this.title = title;
        this.summary = summary;
        this.occurredAt = occurredAt;
    }

    public UUID getId() { return id; }
    public EventCategory getCategory() { return category; }
    public SeverityLevel getSeverity() { return severity; }
    public String getTitle() { return title; }
    public String getSummary() { return summary; }
    public Instant getOccurredAt() { return occurredAt; }
}

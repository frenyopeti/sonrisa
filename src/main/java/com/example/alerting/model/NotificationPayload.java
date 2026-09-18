package com.example.alerting.model;

import java.time.Instant;

public record NotificationPayload(
    String eventId,
    EventCategory category,
    SeverityLevel severity,
    String title,
    String summary,
    Instant occurredAt
) {}

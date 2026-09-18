package com.example.alerting.service;

import com.example.alerting.entity.AlertRule;
import com.example.alerting.entity.EventLog;
import com.example.alerting.model.NotificationPayload;
import com.example.alerting.repository.AlertRuleRepository;
import com.example.alerting.repository.EventLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventIngestionService {

    private final EventLogRepository eventLogRepository;
    private final AlertRuleRepository alertRuleRepository;
    private final AlertDispatchService alertDispatchService;

    public EventIngestionService(EventLogRepository eventLogRepository,
                                 AlertRuleRepository alertRuleRepository,
                                 AlertDispatchService alertDispatchService) {
        this.eventLogRepository = eventLogRepository;
        this.alertRuleRepository = alertRuleRepository;
        this.alertDispatchService = alertDispatchService;
    }

    @Transactional
    public EventLog processIncomingEvent(EventLog event) {
        // 1. Esemény mentése az adatbázisba
        EventLog savedEvent = eventLogRepository.save(event);

        // 2. Aktív szabályok lekérése az adott kategóriára
        List<AlertRule> matchingRules = alertRuleRepository.findByCategoryAndActiveTrue(event.getCategory());

        // 3. Notification Payload előállítása
        NotificationPayload payload = new NotificationPayload(
                savedEvent.getId().toString(),
                savedEvent.getCategory(),
                savedEvent.getSeverity(),
                savedEvent.getTitle(),
                savedEvent.getSummary(),
                savedEvent.getOccurredAt()
        );

        // 4. Szabályok szűrése súlyosság szerint és kiküldés
        for (AlertRule rule : matchingRules) {
            if (event.getSeverity().ordinal() >= rule.getMinSeverity().ordinal()) {
                alertDispatchService.dispatchToRule(savedEvent.getId(), rule, payload);
            }
        }

        return savedEvent;
    }
}
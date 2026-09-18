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
        EventLog savedEvent = eventLogRepository.save(event);

        List<AlertRule> matchingRules = alertRuleRepository.findByCategoryAndActiveTrue(event.getCategory());
        System.out.printf("[INGESTION] Esemény fogadva: %s | Talált szabályok száma: %d%n", 
                event.getTitle(), matchingRules.size());

        NotificationPayload payload = new NotificationPayload(
                savedEvent.getId().toString(),
                savedEvent.getCategory(),
                savedEvent.getSeverity(),
                savedEvent.getTitle(),
                savedEvent.getSummary(),
                savedEvent.getOccurredAt()
        );

        for (AlertRule rule : matchingRules) {
            if (event.getSeverity().ordinal() >= rule.getMinSeverity().ordinal()) {
                System.out.println("[INGESTION] Értesítés kiküldése szabály alapján: " + rule.getId());
                alertDispatchService.dispatchToRule(savedEvent.getId(), rule, payload);
            }
        }

        return savedEvent;
    }
}
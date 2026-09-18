package com.example.alerting.controller;

import com.example.alerting.entity.AlertRule;
import com.example.alerting.entity.DeliveryLog;
import com.example.alerting.entity.EventLog;
import com.example.alerting.repository.AlertRuleRepository;
import com.example.alerting.repository.DeliveryLogRepository;
import com.example.alerting.service.EventIngestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class DashboardController {

    private final EventIngestionService eventIngestionService;
    private final AlertRuleRepository alertRuleRepository;
    private final DeliveryLogRepository deliveryLogRepository;

    public DashboardController(EventIngestionService eventIngestionService,
                               AlertRuleRepository alertRuleRepository,
                               DeliveryLogRepository deliveryLogRepository) {
        this.eventIngestionService = eventIngestionService;
        this.alertRuleRepository = alertRuleRepository;
        this.deliveryLogRepository = deliveryLogRepository;
    }

    @PostMapping("/trigger-event")
    public ResponseEntity<EventLog> triggerManualEvent(@RequestBody EventLog event) {
        EventLog processed = eventIngestionService.processIncomingEvent(event);
        return ResponseEntity.ok(processed);
    }

    @GetMapping("/audit-logs")
    public ResponseEntity<List<DeliveryLog>> getAuditLogs() {
        return ResponseEntity.ok(deliveryLogRepository.findTop50ByOrderByDispatchedAtDesc());
    }

    @GetMapping("/rules")
    public ResponseEntity<List<AlertRule>> getActiveRules() {
        return ResponseEntity.ok(alertRuleRepository.findAll());
    }
}
package com.example.alerting;

import com.example.alerting.entity.AlertRule;
import com.example.alerting.entity.DeliveryLog;
import com.example.alerting.entity.EventLog;
import com.example.alerting.model.ChannelType;
import com.example.alerting.model.DeliveryStatus;
import com.example.alerting.model.EventCategory;
import com.example.alerting.model.SeverityLevel;
import com.example.alerting.repository.AlertRuleRepository;
import com.example.alerting.repository.DeliveryLogRepository;
import com.example.alerting.service.EventIngestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AlertSystemIntegrationTest {

    @Autowired
    private EventIngestionService eventIngestionService;

    @Autowired
    private AlertRuleRepository alertRuleRepository;

    @Autowired
    private DeliveryLogRepository deliveryLogRepository;

    @BeforeEach
    void setup() {
        deliveryLogRepository.deleteAll();
        alertRuleRepository.deleteAll();

        // Teszt szabály létrehozása: BREAKING_NEWS -> Email & Slack
        alertRuleRepository.save(new AlertRule(
                "test-user",
                EventCategory.BREAKING_NEWS,
                SeverityLevel.MEDIUM,
                Set.of(ChannelType.EMAIL, ChannelType.SLACK),
                "test@example.com"
        ));
    }

    @Test
    @DisplayName("End-to-End: Incoming event triggers async notification dispatching")
    void testEventIngestionToDeliveryAudit() {
        // 1. Teszt Esemény létrehozása konstruktor segítségével
        EventLog event = new EventLog(
                EventCategory.BREAKING_NEWS,
                SeverityLevel.HIGH,
                "Critical Database Outage",
                "Primary DB is non-responsive.",
                Instant.now()
        );

        // 2. Ingestion végrehajtása
        EventLog processedEvent = eventIngestionService.processIncomingEvent(event);
        assertNotNull(processedEvent.getId());

        // 3. Aszinkron kiküldés megvárása (max 5 mp)
        await().atMost(5, TimeUnit.SECONDS).until(() -> deliveryLogRepository.count() >= 2);

        // 4. Audit Log ellenőrzés
        List<DeliveryLog> logs = deliveryLogRepository.findAll();
        assertEquals(2, logs.size());

        boolean hasEmail = logs.stream().anyMatch(l -> l.getChannel() == ChannelType.EMAIL && l.getStatus() == DeliveryStatus.SUCCESS);
        boolean hasSlack = logs.stream().anyMatch(l -> l.getChannel() == ChannelType.SLACK && l.getStatus() == DeliveryStatus.SUCCESS);

        assertTrue(hasEmail, "Email csatornának sikerülnie kellett");
        assertTrue(hasSlack, "Slack csatornának sikerülnie kellett");
    }
}
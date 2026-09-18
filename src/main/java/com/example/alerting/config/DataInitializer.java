package com.example.alerting.config;

import com.example.alerting.entity.AlertRule;
import com.example.alerting.model.ChannelType;
import com.example.alerting.model.EventCategory;
import com.example.alerting.model.SeverityLevel;
import com.example.alerting.repository.AlertRuleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initRules(AlertRuleRepository ruleRepository) {
        return args -> {
            if (ruleRepository.count() == 0) {
                // Szabály BREAKING_NEWS kategóriára -> EMAIL és SLACK
                ruleRepository.save(new AlertRule(
                        "user-101",
                        EventCategory.BREAKING_NEWS,
                        SeverityLevel.LOW,
                        Set.of(ChannelType.EMAIL, ChannelType.SLACK),
                        "admin@example.com"
                ));

                // Szabály MARKET_MOVEMENT kategóriára -> SLACK
                ruleRepository.save(new AlertRule(
                        "user-102",
                        EventCategory.MARKET_MOVEMENT,
                        SeverityLevel.MEDIUM,
                        Set.of(ChannelType.SLACK),
                        "https://hooks.slack.com/services/test/webhook"
                ));

                // Szabály NATURAL_DISASTER kategóriára -> EMAIL
                ruleRepository.save(new AlertRule(
                        "user-103",
                        EventCategory.NATURAL_DISASTER,
                        SeverityLevel.LOW,
                        Set.of(ChannelType.EMAIL),
                        "ops@example.com"
                ));

                System.out.println(">>> [INIT] Demo AlertRules sikeresen betöltve az adatbázisba!");
            }
        };
    }
}
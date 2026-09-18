package com.example.alerting.repository;

import com.example.alerting.entity.AlertRule;
import com.example.alerting.model.EventCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AlertRuleRepository extends JpaRepository<AlertRule, UUID> {
    List<AlertRule> findByCategoryAndActiveTrue(EventCategory category);
}

package com.example.alerting.repository;

import com.example.alerting.entity.DeliveryLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DeliveryLogRepository extends JpaRepository<DeliveryLog, UUID> {
    List<DeliveryLog> findTop50ByOrderByDispatchedAtDesc();
}

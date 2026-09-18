
# 02. System Architecture & Extensibility Design

## 1. High-Level Architecture Overview

The system is built on **Java 21** and **Spring Boot 3.x** using an event-driven, non-blocking architecture. It strictly decouples event ingestion, subscription matching, and notification dispatching.

```text
               ┌───────────────────────────────┐
               │ External API / Admin Trigger  │
               └───────────────┬───────────────┘
                               │ (REST POST / events)
                               ▼
               ┌───────────────────────────────┐
               │    Event Ingestion Service    │
               └───────────────┬───────────────┘
                               │ (Publishes System Event)
                               ▼
               ┌───────────────────────────────┐
               │  Subscription Matcher Engine  │
               └───────────────┬───────────────┘
                               │ (Finds matching AlertRules)
                               ▼
               ┌───────────────────────────────┐
               │  Async Alert Dispatcher Engine│
               └───────────────┬───────────────┘
                               │ (Uses NotificationChannelRegistry)
               ┌───────────────┴───────────────┐
               ▼                               ▼
    ┌────────────────────┐          ┌────────────────────┐
    │EmailNotification   │          │SlackNotification   │
    │    Channel         │          │    Channel         │
    └────────────────────┘          └────────────────────┘
               │                               │
               └───────────────┬───────────────┘
                               ▼
               ┌───────────────────────────────┐
               │ Delivery Log Audit Repository │
               └───────────────────────────────┘
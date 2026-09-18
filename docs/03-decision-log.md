# 03. Decision Log (Architecture Decision Records)

## ADR 001: Decoupling Event Detection from Notification Dispatching

* **Status:** Accepted
* **Date:** 2026-09-18
* **Context:**
  The product brief requires alerting users when "something important happens in the world" (e.g., breaking news, market movements). However, no event sources, ingestion pipelines, or detection criteria were provided.
* **Considered Options:**
    1. *Option A (Embedded Scrapers):* Build custom RSS/news scrapers and market API pollers inside the alert service.
    2. *Option B (Decoupled Event-Driven Ingestion):* Expose a generic REST Ingestion API and Spring Event publisher, treating all events as standardized JSON payloads regardless of their origin.
* **Decision:**
  We choose **Option B (Decoupled Event-Driven Ingestion)**. The alerting engine will remain entirely agnostic to how events are detected. An external ingestor or the Admin Panel will push events adhering to a unified contract:
  ```json
  {
    "eventId": "evt_987654",
    "category": "MARKET_MOVEMENT",
    "severity": "HIGH",
    "title": "S&P 500 drops 3%",
    "summary": "Sudden market contraction triggered by inflation data.",
    "occurredAt": "2026-09-18T10:00:00Z"
  }
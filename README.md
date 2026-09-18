# ⚡ Intelligent Alerting System & Monitoring Dashboard

> **Evaluation Note:** This repository contains the complete process, artifacts, decision logs, and code built from a vague brief to a working prototype.

## Overview
This system enables users to create customizable alerts for global events (news, market movements, natural disasters) and receive notifications across extensible channels (Email, Slack, with support for future integrations). It includes an Admin View for monitoring system health, managing event triggers, and inspecting delivery logs.

A resilient, scalable Spring Boot microservice for ingestion, filtering, and multi-channel dispatching of alert events.

## 🚀 Main Features
* **Asynchronous Processing**: Non-blocking alert dispatch using Spring `@Async` and `CompletableFuture`.
* **Strategy Pattern & Extensibility**: Loosely-coupled notification channels (`Email`, `Slack`).
* **Interactive UI**: Embedded Bootstrap dashboard for manual event triggering and real-time delivery audit logging.
* **Audit Logging**: End-to-end tracking of notification delivery status in the database.

## 🛠️ Tech Stack
* **Java**: 21
* **Framework**: Spring Boot 3.2+
* **Database**: H2 (In-Memory)
* **Build Tool**: Gradle
* **Testing**: JUnit 5, Awaitility

## 🏃 Getting Started

### Prerequisites
* JDK 21 installed.

### Build and Run
1. Clone the repository:
   \`\`\`bash
   git clone <repository-url>
   cd sonrisa
   \`\`\`
2. Run the application:
   \`\`\`bash
   ./gradlew bootRun
   \`\`\`
3. Open the Dashboard in your browser:
   [http://localhost:8080/index.html](http://localhost:8080/index.html)

## 🧪 Running Tests
Execute unit and integration tests:
\`\`\`bash
./gradlew test
\`\`\`
"@

[System.IO.File]::WriteAllText("$PWD/README.md", $readme, (New-Object System.Text.UTF8Encoding($false)))
Write-Host "README.md sikeresen elkészült!" -ForegroundColor Green


## Repository Structure
```text
├── docs/
│   ├── 01-plan-of-attack.md        # Scoping, assumptions, and execution roadmap
│   ├── 02-architecture-design.md   # Data model, API specs, and channel plugin interfaces
│   ├── 03-decision-log.md          # Key architectural and product decisions (ADRs)
│   └── 04-ai-critique-log.md       # AI evaluation log: hallucinations, rejections, and course-corrections
├── prompts/
│   └── prompt-history.md           # Full chronological log of prompts and AI interactions
├── src/                            # Implementation codebase
├── tests/                          # Unit, integration, and sanity tests
└── README.md
```

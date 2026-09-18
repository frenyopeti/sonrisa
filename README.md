# Intelligent Alerting System — Feature Design & Prototype

> **Evaluation Note:** This repository contains the complete process, artifacts, decision logs, and code built from a vague brief to a working prototype.

## Overview
This system enables users to create customizable alerts for global events (news, market movements, natural disasters) and receive notifications across extensible channels (Email, Slack, with support for future integrations). It includes an Admin View for monitoring system health, managing event triggers, and inspecting delivery logs.

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

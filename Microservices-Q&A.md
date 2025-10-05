## 1. Service-to-service communication is slow, causing latency. What do you do?

Use async messaging (Kafka).

Add caching (Redis).

Use gRPC instead of REST for faster binary communication.

## 2. Kafka broker is down, messages are getting lost. Solution?

Enable acks=all + replication factor.

Use Dead Letter Queue (DLQ) for failed messages.

Auto-retry with backoff.

## 3. One service is overloaded while others are fine. How to scale?

Apply Kubernetes HPA (Horizontal Pod Autoscaler).

Use load balancing (API Gateway, Envoy).

Offload heavy tasks to async processing.

## 4. How to migrate a database schema without downtime?

Backward-compatible schema changes (add new columns, don’t drop old ones).

Expand & contract pattern (deploy schema first, then update code).

Feature toggles for gradual migration.

## 5. A service dependency is flaky. How do you prevent cascading failures?

Use Circuit Breaker (Resilience4j).

Apply Bulkhead pattern to isolate failures.

Use fallback responses.

## 6. How do you ensure idempotency in APIs?

Generate unique request IDs (UUID, transaction ID).

Store processed requests in DB/cache to avoid duplicate processing.

## 7. How do you roll out new versions without downtime?

Blue/Green deployments → switch traffic instantly.

Canary releases → route small % of traffic first.

Kubernetes rolling updates.

## 8. A service call is timing out. What’s your approach?

Check logs & tracing (Jaeger/Zipkin).

Tune timeout & retry policies.

Use async event-driven design if possible.

## 9. How to secure service-to-service communication?

mTLS (mutual TLS).

Service mesh (Istio, Linkerd).

OAuth2 + JWT for token-based auth.

## 10. One service requires data from multiple DBs. How do you handle?

Use API Composition (aggregator service calls multiple services).

Use CQRS + event-driven updates to maintain local read models.

## 11. How do you test inter-service compatibility?

Contract testing with Pact.

Maintain API versioning.

Use consumer-driven tests.

## 12. You need to support millions of users. How do you optimize authentication?

Use stateless JWT tokens.

Centralized Auth server (Keycloak, Okta).

Cache auth results (Redis).

## 13. What if Redis cache goes down?

Fallback to DB (graceful degradation).

Use cache warming after restart.

Add multi-tier caching (local + distributed).

## 14. How do you implement distributed logging?

Use ELK stack (Elasticsearch, Logstash, Kibana).

Add correlation IDs in logs.

Standardize JSON log format.

## 15. How do you achieve strong consistency across services?

Avoid it unless critical.

Use 2PC (rare, slow) only when necessary.

Prefer Saga pattern for eventual consistency.

## 16. A service memory usage keeps increasing. How do you debug?

Analyze with jmap, jstat, jvisualvm.

Look for memory leaks (unclosed DB connections, caches).

Enable heap dump on OOM and inspect.

17. How to handle API versioning in microservices?

URI-based (/v1/orders).

Header-based (Accept: application/vnd.company.v1+json).

Backward-compatible changes to minimize versions.

18. What if DB is a bottleneck?

Apply sharding & replication.

Introduce CQRS.

Add caching layer.

Move to event sourcing if suitable.

19. How do you trace a request across 10+ microservices?

Use Distributed Tracing (OpenTelemetry, Sleuth, Jaeger).

Pass trace IDs in headers.

Centralized dashboards.

## 20. How do you handle GDPR or data privacy in microservices?

Encrypt PII in DB and at rest.

Anonymize logs (don’t log sensitive data).

Data deletion API for right-to-erasure.

## 21. Q: What if one microservice goes down in production?
A: Use circuit breaker (Resilience4j) + fallback response, degrade gracefully. Monitor via Prometheus alerts.

## 22. Q: How do you migrate schema changes in production without downtime?
A: Use blue-green DB deployment, backward-compatible schema changes, and feature toggles. Example: add new column as nullable, update code, then enforce constraints later.

## 23. Q: What if Kafka is down and services rely on it?
A: Enable retry with backoff.

Use DLQ (Dead Letter Queue).

Store events in local DB/cache until Kafka recovers.

## 24. Q: How do you release a new API version without breaking existing clients?
A: Support versioning (v1/v2) or backward-compatible changes (don’t remove fields, mark deprecated).

## 25. Q: A REST call between services adds high latency. What would you do?
A: Switch to async event-driven (Kafka/gRPC streaming).

Cache responses (Redis).

Tune connection pooling (HikariCP, Netty).

## 26. Q: How do you ensure consistency across multiple microservices updating different DBs?
A: Saga Pattern (choreography/orchestration) → compensating transactions instead of 2PC.

## 27. Q: Your service queries the same data repeatedly. How do you optimize?
A: Use Redis / in-memory caching with TTL + cache invalidation policies (write-through or event-driven updates).

## 28. Q: How do services authenticate with each other?
A: Use mTLS (mutual TLS) or JWT tokens issued by an Identity Provider (Keycloak/OAuth2).

## 29. Q: Only one service struggles under high load. What’s your approach?
A: Scale only that service (K8s HPA), optimize code, add caching, and use async processing.

## 30. Q: Your API Gateway becomes a bottleneck. What now?
A: Scale horizontally.

Introduce service mesh (Istio/Linkerd) for direct routing.

Enable rate limiting & circuit breaking at gateway.

## 31. Q: How do you trace a user request across 10+ microservices?
A: Use correlation IDs (traceId, spanId) with distributed tracing (OpenTelemetry, Jaeger, Zipkin).

## 32. Q: Your system relies on a flaky third-party API. How do you handle it?
A: Circuit breaker + retries.

Cache last successful response.

Queue requests & retry asynchronously.

## 33. Q: Two microservices show inconsistent data. How do you fix it?
A: Use event sourcing or CDC (Change Data Capture).

Ensure idempotency in event handling.

Implement reconciliation jobs.

## 34. Q: How do you roll back a failed deployment?
A: Keep old version running (Blue/Green). Roll back traffic via feature flag or routing switch in Kubernetes.

## 35. Q: How do you prevent a single client from overwhelming your services?
A: Implement rate limiting (token bucket, leaky bucket) at API Gateway (Kong, Spring Cloud Gateway).

## 36. Q: How do you handle huge payloads between services (e.g., 50MB JSON)?
A: Switch to gRPC with Protobuf (binary, smaller size).

Chunk uploads.

Store in S3-like storage, pass reference IDs instead of raw payload.

## 37. Q: How do you migrate a monolith to microservices?
A: Use Strangler Fig Pattern → gradually carve out modules as microservices, integrate via API Gateway.

## 38. Q: How do you test microservices that depend on other services?
A: Unit Tests → isolated.

Contract Tests (Pact).

Integration Tests with Testcontainers.

Stub/mock services for dependencies.

## 39. Q: How do you manage DB passwords and API keys securely?
A: Use Vault / K8s Secrets / AWS Secrets Manager, never hardcode. Rotate periodically.

## 40. Q: A service misbehaves only in production. How do you debug?
A: Use centralized logging (ELK/EFK).

Distributed tracing to identify latency issues.

Metrics dashboards (Grafana, Prometheus).

Feature flagging to roll back risky code.

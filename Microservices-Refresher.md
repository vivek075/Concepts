# Q1. What are microservices, and why do organizations prefer them over monoliths?

Answer:

Definition: An architectural style where applications are built as a collection of small, independently deployable services.

Why better than monoliths:

Independent deployability → faster releases.

Fault isolation → one service failure doesn’t crash the whole system.

Technology flexibility → each service can use the best-fit stack.

Scalability → scale only the bottleneck service.

Trade-offs:

Increased operational complexity (service discovery, observability, distributed data).

Higher infra cost.

# Q2. How do you design communication between microservices?
Answer:

Sync (blocking): REST, gRPC → useful for real-time requests.

Async (non-blocking): Kafka, RabbitMQ → best for event-driven systems.

Best practice: Favor event-driven async communication to reduce coupling.

Java Example:

REST → Spring Boot + Feign Client.

Async → Spring Cloud Stream with Kafka.

# Q3. How do you handle service discovery?
Answer:

Options:

Service registry (Eureka, Consul, Zookeeper).

DNS-based discovery (Kubernetes Services).

Java Example: Spring Cloud Netflix Eureka with Ribbon load balancing.

Cloud-native: Kubernetes + Istio service mesh.

# Q4. How do you handle databases in microservices?
Answer:

Principle: Database-per-service → avoids tight coupling.

Patterns:

Saga Pattern → distributed transactions.

CQRS → separate read/write models.

Event Sourcing → reconstruct state from events.

Challenge: Data consistency → solve using eventual consistency and compensating transactions.

# Q5. How do you scale a microservice that handles 100k+ requests/sec?
Answer:

Scale horizontally: Kubernetes auto-scaling.

Optimize at code level: Use Netty-based frameworks (Spring WebFlux), reduce GC pauses.

Caching: Redis for frequently accessed data.

Async processing: Kafka for buffering spikes.

Circuit Breakers: Resilience4j / Hystrix to prevent cascading failures.

# Q6. How do you make microservices fault-tolerant?
Answer:

Circuit Breaker (Resilience4j).

Retry with exponential backoff.

Bulkhead pattern → isolate resources.

Rate limiting (API Gateway or Envoy).

Idempotency in APIs to safely retry requests.

# Q7. What is the role of an API Gateway in microservices?
Answer:

Acts as an entry point for all requests.

Features:

Routing, Load Balancing.

Authentication & Authorization (JWT, OAuth2).

Rate limiting & throttling.

Caching.

Examples: Spring Cloud Gateway, Kong, Zuul, NGINX.

# Q8. How do you secure microservices?
Answer:

AuthN/AuthZ: OAuth2, OpenID Connect (Keycloak, Okta).

Transport security: TLS everywhere.

Service-to-service security: mTLS in Istio.

API tokens: JWT for stateless authentication.

Secret management: HashiCorp Vault, AWS Secrets Manager.

# Q9. How do you monitor microservices in production?
Answer:

Metrics: Prometheus + Grafana.

Logging: ELK Stack (Elasticsearch, Logstash, Kibana) / OpenSearch.

Tracing: Distributed tracing with OpenTelemetry, Jaeger, Zipkin.

Alerting: Prometheus AlertManager, PagerDuty.

# Q10. How do you deploy microservices in production?
Answer:

Containerization: Docker.

Orchestration: Kubernetes.

CI/CD: Jenkins, GitHub Actions, ArgoCD.

Blue/Green & Canary deployments to minimize downtime.

Infra-as-Code: Terraform, Helm charts.

# Q11. How do you test microservices effectively?
Answer:

Unit Tests: JUnit, Mockito.

Contract Testing: Pact to ensure compatibility.

Integration Tests: Testcontainers for DB/MQ.

End-to-End: Cucumber, Selenium.

Chaos Testing: Netflix Chaos Monkey to test resilience.

# Q12. What are common pitfalls in microservices adoption?
Answer:

Too many small services → complexity explosion.

Overusing synchronous REST → cascading failures.

Poor observability → hard to debug.

Wrong database strategy → coupling.

Lack of strong DevOps maturity → deployment bottlenecks.

# Q13. If one microservice goes down, how do you ensure the system still works?
Answer:

Fallbacks (Resilience4j).

Eventual consistency instead of strict sync calls.

Graceful degradation → partial functionality instead of total failure.

# Q14. How do you implement distributed transactions in microservices?
Answer:

Saga pattern (preferred): Each service does local transaction + publishes event. Other services act accordingly. If failure → compensating actions.

Two-Phase Commit (2PC): Rare, not recommended due to blocking and scalability issues.

# Q15. How do you handle schema evolution in microservices?
Answer:

Use backward-compatible changes.

Schema Registry (for Avro, Protobuf in Kafka).

API versioning → v1, v2 endpoints.

Feature toggles → gradual rollout.

# Q16. Which Java frameworks are commonly used in microservices?
Answer:

Spring Boot + Spring Cloud (Netflix OSS, Spring Cloud Gateway, Config Server, Sleuth, Resilience4j).

Quarkus / Micronaut (faster startup, GraalVM support).

Dropwizard (lightweight).

# Q17. How would you optimize a Spring Boot microservice for low latency?
Answer:

Use Spring WebFlux (reactive, non-blocking).

Reduce GC overhead → G1/ZGC.

Connection pooling (HikariCP).

Async logging (Logback AsyncAppender).

Pre-warm JVM (class loading, JIT compilation).

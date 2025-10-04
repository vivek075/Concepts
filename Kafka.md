# Kafka Basics

## Q1. What is Apache Kafka and why is it used?

Kafka is a distributed event streaming platform used for high-throughput, low-latency, real-time data pipelines and streaming applications.

Use cases: log aggregation, stream processing, messaging, event sourcing, microservices communication.

## Q2. How is Kafka different from traditional messaging systems like JMS, RabbitMQ?

Kafka is pull-based, whereas JMS is often push-based.

Kafka stores data durably on disk (commit log).

Kafka is designed for high throughput and can handle millions of messages/sec.

Kafka decouples producers and consumers with scalable partitions.

## Q3. What are Kafka Topics and Partitions?

Topic = logical stream of messages.

Partition = split of a topic for parallelism.

Each partition is an ordered immutable log.

## Q4. What is an offset in Kafka?

Offset = unique sequential ID assigned to each message in a partition.

Consumers use offsets to track their position.

## Q5. How does Kafka provide durability?

Messages are persisted on disk.

Replication across multiple brokers.

Configurable acks and min.insync.replicas.

# 2. Kafka Architecture

## Q6. What are the main components of Kafka?

Producer → publishes data.

Consumer → reads data.

Broker → Kafka server, stores data.

Zookeeper (legacy) → manages metadata (replaced by KRaft in newer versions).

Controller → leader election, partition reassignment.

## Q7. What is a Kafka Broker?

A Kafka server that handles storage and client requests.

Cluster typically has multiple brokers for scalability.

## Q8. What is a Kafka Cluster?

Group of brokers working together to provide distributed messaging.

## Q9. What is the role of Zookeeper in Kafka?

Manages cluster metadata, leader election, configuration.

Newer versions of Kafka use KRaft (Kafka Raft Metadata mode), removing Zookeeper dependency.

## Q10. What is the Kafka Controller?

A special broker responsible for leader election of partitions.

# 3. Producers & Consumers

## Q11. How does a Kafka Producer work?

Sends messages to a broker → broker appends message to topic partition.

Can specify partition key (hash-based).

Configurable retries, batching, acknowledgements.

## Q12. What are Producer Acknowledgements (acks)?

acks=0: Fire and forget, no guarantee.

acks=1: Leader acknowledgment only.

acks=all or acks=-1: All replicas acknowledge → strongest durability.

## Q13. What is Idempotent Producer in Kafka?

Ensures no duplicate messages when retries happen.

Enabled via enable.idempotence=true.

## Q14. What is Kafka Consumer Group?

Group of consumers working together to consume from topic partitions.

Each partition is consumed by only one consumer within the group.

## Q15. What is Consumer Rebalance?

Redistribution of partitions among consumers when a consumer joins/leaves.

## Q16. How does Kafka achieve backpressure handling?

Consumers pull messages at their own rate.

If slow → lag builds up in consumer group.

## Q17. How do Kafka consumers commit offsets?

Automatic commit (default, periodic).

Manual commit (developer-controlled for better reliability).

# 4. Kafka Storage & Performance

## Q18. How does Kafka store messages on disk?

Sequential append-only log files per partition.

Data files are segment-based for efficient cleanup.

## Q19. What is Log Retention Policy in Kafka?

Kafka can delete or compact old messages:

Delete Policy → remove messages after retention.ms.

Compact Policy → keep only latest message per key.

## Q20. How does Kafka achieve high throughput?

Sequential disk writes.

Page cache usage.

Zero-copy transfer (sendfile).

Batching & compression.

## Q21. What is Kafka Message Compression?

Supported algorithms: gzip, snappy, lz4, zstd.

Reduces network bandwidth, increases throughput.

# 5. Replication & Fault Tolerance

## Q22. How does Kafka achieve fault tolerance?

Data replication across multiple brokers.

Partition has leader and followers.

## Q23. What is ISR (In-Sync Replicas)?

Set of replicas that are fully caught up with the leader.

min.insync.replicas defines minimum required for successful writes.

## Q24. What happens when a Kafka broker goes down?

Partition leaders are re-elected.

Followers take over if in ISR.

## Q25. How does Kafka handle data consistency?

Configurable durability (acks, replication).

Writes to leader are replicated to followers.

# 6. Kafka Streams & Connect

## Q26. What is Kafka Streams?

A Java library for real-time stream processing on top of Kafka.

Provides stateful operations (join, windowing, aggregation).

## Q27. What is Kafka Connect?

Framework to connect Kafka with external systems (DBs, cloud storage, Elasticsearch).

Supports Source Connectors (import) and Sink Connectors (export).

## Q28. Difference between Kafka Streams and Spark Streaming / Flink?

Kafka Streams → lightweight, JVM-based, embedded library.

Spark/Flink → heavy cluster frameworks with broader capabilities.

# 7. Transactions, Exactly-Once, and DLQ

## Q29. What is Exactly Once Semantics (EOS) in Kafka?

Prevents message duplication and loss.

Achieved using idempotent producer + transactional APIs.

## Q30. What is a Dead Letter Queue (DLQ) in Kafka?

A separate topic for failed/poison messages.

Useful for debugging & retries.

## Q31. How does Kafka ensure message ordering?

Messages are ordered within a partition but not across partitions.

# 8. Security

## Q32. What security mechanisms does Kafka support?

Authentication: SASL (PLAIN, SCRAM, Kerberos), SSL.

Authorization: ACLs (topic-level, group-level).

Encryption: SSL/TLS.

## Q33. How do you secure Kafka in production?

Enable TLS for encryption in transit.

Use SASL for authentication.

Use ACLs for access control.

Enable auditing and monitoring.

# 9. Monitoring & Operations

## Q34. How do you monitor Kafka?

Metrics via JMX.

Prometheus + Grafana dashboards.

Kafka Manager, Confluent Control Center.

## Q35. What are common Kafka bottlenecks?

Disk I/O.

Network bandwidth.

Under-replicated partitions.

Large message sizes.

## Q36. How to tune Kafka performance?

Increase partition count.

Use compression.

Tune num.network.threads, num.io.threads.

Batch messages in producer.

# 10. Advanced & System Design

## Q37. How do you design Kafka for high availability?

Replication factor ≥ 3.

Multi-broker, multi-rack setup.

Enable rack awareness.

## Q38. How do you handle Kafka message ordering across partitions?

Use partition key to ensure same key always goes to same partition.

## Q39. How to handle large messages in Kafka (>1MB)?

Tune max.message.bytes.

Use external storage (S3, HDFS) + store references in Kafka.

## Q40. How to migrate Kafka without downtime?

MirrorMaker (Kafka → Kafka replication).

Dual-write strategy during migration.

## Q41. How does Kafka support microservices?

Event-driven communication.

Loose coupling.

Integration via Kafka Streams, Connect.

## Q42. How does Kafka differ from databases?

Kafka is a log-based storage (append-only).

Not designed for random reads/updates.

Used as a source of truth for events (event sourcing).

## Q43. How do banks use Kafka in real-time systems?

Trade processing.

Payment settlements.

Fraud detection (real-time anomaly detection).

Audit and compliance logs.

---
---

# 1. Kafka Architecture & Design

## Q1. How does Kafka guarantee message durability in case of broker failure?
Kafka persists messages on disk and replicates across brokers. Producers can use acks=all + min.insync.replicas to ensure messages are acknowledged by multiple brokers before being considered committed.

## Q2. In production, how do you decide the number of partitions for a topic?

Depends on throughput, parallelism, and consumer scaling.

Rule of thumb: partitions = peak throughput / single partition throughput.

Over-partitioning leads to overhead (open file handles, memory usage).

## Q3. What is ISR (In-Sync Replica) and why is it critical?
ISR = set of replicas fully caught up with the leader. Writes succeed only if min.insync.replicas is satisfied. If ISR shrinks below threshold, Kafka rejects writes to avoid data loss.

## Q4. How would you design Kafka to achieve zero data loss?

Producer: acks=all, enable.idempotence=true.

Topic: replication factor ≥ 3, min.insync.replicas=2.

Consumers: use manual commits after processing.

Monitor ISR and avoid unclean leader election.

## Q5. How does Kafka ensure ordering of messages?

Kafka guarantees ordering only within a partition.

To enforce order across events, use a partition key (e.g., accountId for payments).

# 2. Producers & Consumers

## Q6. What’s the role of an idempotent producer?
Prevents duplicates when retries occur. Kafka assigns a producer ID (PID) and sequence numbers per partition to discard duplicates.

## Q7. What is exactly-once delivery in Kafka?
Achieved by idempotent producer + transactions.

Producer writes messages within a transaction.

Consumer commits offsets atomically with writes.

Prevents both duplication and loss.

## Q8. How do you handle consumer group rebalancing in production?

Use static group membership (assign stable group.instance.id).

Reduce rebalance frequency via session.timeout.ms, heartbeat.interval.ms.

Consider incremental cooperative rebalancing (partition.assignment.strategy=CooperativeStickyAssignor).

## Q9. How do you tune Kafka consumers for high throughput?

Increase fetch.min.bytes and fetch.max.wait.ms for batching.

Use max.poll.records to control processing batch size.

Enable async commits for better throughput.

## Q10. How do you ensure consumers don’t reprocess data after restart?

Use manual offset commits only after processing is complete.

Store processing status in external DB if exactly-once isn’t achievable.

# 3. Storage, Performance & Retention

## Q11. How does Kafka achieve high throughput compared to JMS/RabbitMQ?

Sequential disk I/O.

OS page cache.

Zero-copy (sendfile).

Partition-based parallelism.

## Q12. How do you configure Kafka for long-term message storage?

Use log.retention.hours, log.retention.bytes.

Consider log compaction for compacted topics (e.g., latest account balance).

For archival, use Kafka Connect → S3/HDFS.

## Q13. What happens when a Kafka topic has too many small partitions?

Metadata overhead on controller.

Increased open file descriptors.

Frequent rebalances take longer.

Recommended: avoid > 200k partitions per cluster.

## Q14. How do you monitor consumer lag effectively?

Track records-lag-max via JMX.

Use tools like Burrow or Kafka Exporter (Prometheus).

High lag may indicate slow consumers or undersized partitions.

## Q15. What are batch.size and linger.ms in producers?

batch.size: max size of batch (bytes).

linger.ms: wait time to accumulate more records before sending.

Together, they help optimize throughput vs latency.

# 4. Replication & HA

## Q16. How does Kafka handle broker failure?

Controller elects a new partition leader from ISR.

If ISR empty, and unclean.leader.election.enable=true, Kafka may elect out-of-sync replica (risk of data loss).

## Q17. What is rack awareness in Kafka replication?

Ensures replicas are placed across different racks/availability zones.

Prevents data loss if one rack fails.

## Q18. How do you configure Kafka for high availability in multi-datacenter setups?

Use MirrorMaker 2 for replication.

Or use Confluent Replicator.

Typically: active-active or active-passive replication strategies.

## Q19. How do you tune replication for performance?

Increase replica.fetch.max.bytes.

Set replica.lag.time.max.ms carefully.

Ensure follower replication is not bottlenecked by slow disks.

# 5. Kafka Streams & Connect

## Q20. Difference between Kafka Streams and Kafka Connect?

Streams: library for processing data inside applications.

Connect: integration framework to connect Kafka with external systems (DBs, S3, Elastic).

## Q21. How do you achieve stateful stream processing in Kafka Streams?

Using RocksDB-backed state stores.

Supports windowing, joins, and aggregations.

## Q22. How do you handle stream state recovery after failure?

Kafka Streams checkpoints state to changelog topics.

On restart, it restores from changelog.

## Q23. What are common pitfalls with Kafka Connect in production?

Connector task imbalance.

Handling large payloads.

Backpressure when sink systems are slow.

# 6. Security & Compliance

## Q24. How do you secure Kafka in production?

TLS for encryption.

SASL/SCRAM for authentication.

ACLs for authorization.

Role-based access with fine-grained topic/group permissions.

## Q25. How would you audit Kafka for compliance in a bank?

Enable audit logging for producer/consumer access.

Use Confluent RBAC or Apache Ranger.

Persist DLQ for failed events (important for regulatory compliance).

## Q26. How do you prevent replay attacks in Kafka?

Use unique event IDs.

Maintain deduplication logic downstream.

Secure producer APIs with SASL/OAuth.

# 7. Troubleshooting & Operations

## Q27. A consumer is reading very slowly; how do you troubleshoot?

Check consumer lag metrics.

Verify if processing logic (DB writes, transformations) is slow.

Increase max.poll.records or add more consumers.

## Q28. Producers are timing out frequently. What could be the cause?

Broker under high load.

ISR shrinkage.

Insufficient request.timeout.ms.

Network latency.

## Q29. What is the impact of increasing replication factor?

More durability, but:

Increased network usage.

Slower writes.

Higher storage costs.

## Q30. How do you debug under-replicated partitions (URP)?

Check slow broker disk/network.

Increase replication threads.

Replace failing broker.

## Q31. What happens if producer sends messages faster than broker can handle?

Producer buffers fill (buffer.memory).

max.block.ms may block producers.

Eventually, RecordTooLargeException or dropped messages if retries disabled.

# 8. Advanced System Design

## Q32. How would you design a Kafka system to handle 1M+ messages/sec?

Multiple brokers with SSDs.

Partition count tuned for parallelism.

Compression enabled (Snappy/LZ4).

Batched producer sends (batch.size, linger.ms).

Consumers with parallel workers.

## Q33. How do you guarantee exactly-once delivery in a payment system using Kafka?

Producers use transactions.

Consumers commit offsets within the same transaction as DB updates.

Use idempotent writes in DB.

## Q34. How do you handle schema evolution in Kafka?

Use Schema Registry (Avro/Protobuf/JSON).

Support backward and forward compatibility.

## Q35. How do you design a Dead Letter Queue (DLQ) in Kafka?

Consumers push failed messages into a separate DLQ topic.

Include error metadata (timestamp, cause).

Monitor DLQ and replay messages after fix.

## Q36. How do you implement rate limiting for consumers?

Throttle consumption by adjusting poll size.

Use external rate-limiting libraries (e.g., Guava RateLimiter).

Apply quotas in Kafka broker config (consumer_byte_rate).

## Q37. How do you handle multi-tenancy in Kafka?

Topic-level quotas.

Separate consumer groups.

ACLs for access isolation.

## Q38. How do you design Kafka for cross-region replication with low latency?

Use MirrorMaker 2 async replication (low overhead but eventual consistency).

Or use Confluent’s Cluster Linking for stronger guarantees.

## Q39. What are idempotent consumers and how do you design them?

Consumers that handle duplicate messages gracefully.

Achieved by deduplication keys or transaction logs.

## Q40. How do you migrate Kafka clusters without downtime?

Use MirrorMaker for dual writes.

Cut over consumers after data sync.

Use topic renaming/versioning strategy.

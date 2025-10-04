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

# 9. Real-World & Banking-Specific

## Q41. How is Kafka used in trade settlement systems?

Trades flow into Kafka from upstream systems.

Consumers validate, enrich, and persist to settlement DB.

Downstream risk/fraud detection also consumes from same topics.

## Q42. How do you ensure order of trade events in Kafka across services?

Partition by tradeId to guarantee ordering.

Consumers maintain idempotency in case of retries.

## Q43. In banking, how do you ensure Kafka messages are never lost due to compliance needs?

Replication factor 3+.

Archive topics to S3/HDFS for long-term storage.

No unclean leader elections.

## Q44. How do you integrate Kafka with legacy MQ (IBM MQ, TIBCO)?

Use Kafka Connect source/sink connectors.

Or custom bridges (Kafka consumer → MQ producer).

## Q45. How do you test resiliency of Kafka in production?

Chaos testing (kill brokers).

Simulate network partitions.

Test consumer lag recovery under stress.

# Chapter 1: Kafka Basics (Q1–Q15)

## Q1. What is Apache Kafka?
A distributed event streaming platform for real-time pipelines and event-driven applications.

## Q2. Difference between Kafka and traditional messaging systems (JMS/RabbitMQ)?

Kafka = high throughput, partitioned log, durable storage.

JMS = push-based, slower, not designed for big data scale.

## Q3. What is a Kafka Topic?
Logical channel where producers write messages and consumers read.

## Q4. What are Kafka Partitions?

Ordered, immutable logs.

Enable scalability and parallelism.

## Q5. What is an Offset in Kafka?
A unique sequential ID per message in a partition.

## Q6. What is a Broker in Kafka?
Kafka server that stores data and serves clients.

## Q7. What is a Kafka Cluster?
Group of brokers working together.

## Q8. What is ZooKeeper’s role in Kafka?
Cluster metadata, leader election (replaced by KRaft in new versions).

## Q9. Difference between Kafka Controller and Broker?
Controller manages partition leaders, brokers handle storage/requests.

## Q10. What are Kafka Producers and Consumers?

Producer = sends events.

Consumer = reads events.

## Q11. Difference between push vs pull in Kafka consumers?
Kafka uses pull model, giving consumers control.

## Q12. What is a Consumer Group?
Set of consumers balancing partitions.

## Q13. How does Kafka handle slow consumers?
Messages retained until retention expires → lag increases.

## Q14. What is Kafka’s delivery guarantee?

At most once.

At least once (default).

Exactly once (with idempotence + transactions).

## Q15. What is Kafka’s commit log architecture?
Immutable append-only log, consumers track offset.

# Chapter 2: Kafka Producers (Q16–Q30)

## Q16. How does Kafka decide partition placement for a message?

Key-based hash.

Round robin if no key.

## Q17. What are Producer Acks?

acks=0, acks=1, acks=all.

## Q18. What is an Idempotent Producer?
Guarantees no duplicates when retries occur.

## Q19. What is a Transactional Producer?
Enables atomic writes to multiple partitions + offsets.

## Q20. How does batching improve producer throughput?
Accumulating records into batch.size before sending.

## Q21. What is linger.ms in Kafka Producer?
Delay before sending batch → increases throughput.

## Q22. How does compression help Kafka producers?
Reduces payload size → network + disk efficiency.

## Q23. Common compression algorithms in Kafka?
Gzip, Snappy, LZ4, Zstd.

## Q24. What is max.in.flight.requests.per.connection?
Controls retries and ordering guarantees.

## Q25. How does producer handle retries?
retries, retry.backoff.ms configs.

## Q26. What is delivery.timeout.ms?
Upper bound on message delivery attempts.

## Q27. What happens if producer buffer.memory is full?
Producer blocks or throws exception.

## Q28. How do producers achieve load balancing?
By distributing messages across partitions.

## Q29. How do producers handle large messages?
Tune max.request.size or store externally.

## Q30. How do you ensure ordering in Kafka producers?
Use same key → same partition.

# Chapter 3: Kafka Consumers (Q31–Q50)

## Q31. What is consumer offset commit?
Position tracking to resume processing.

## Q32. Difference between auto vs manual commit?

Auto → risk of reprocessing.

Manual → precise control.

## Q33. What is max.poll.records?
Number of messages per poll.

## Q34. What is max.poll.interval.ms?
Timeout before consumer considered dead.

## Q35. What happens during consumer rebalance?
Partitions redistributed across group.

## Q36. How do you reduce rebalance disruptions?
Static membership, cooperative rebalance.

## Q37. What is partition.assignment.strategy?
How partitions are distributed: Range, RoundRobin, Sticky.

## Q38. How do you tune consumer throughput?
Increase fetch.min.bytes, fetch.max.wait.ms.

## Q39. How do you handle duplicate processing in consumers?
Idempotent consumers, external deduplication.

## Q40. What is consumer lag?
Difference between last produced offset vs last committed offset.

Q41. How do you monitor consumer lag?
JMX, Prometheus, Kafka Burrow.

Q42. What is heartbeat.interval.ms?
How often consumer sends heartbeat to group coordinator.

Q43. What happens if consumer doesn’t commit offsets?
On restart, reprocesses from last committed.

Q44. How do consumers achieve backpressure handling?
Control poll rate, tune max.poll.records.

Q45. What is cooperative rebalancing?
Incremental rebalance, avoids full partition reassignment.

Q46. How do you handle poison messages?
Send to Dead Letter Queue (DLQ).

Q47. How do you guarantee exactly-once with consumers?
Consume + process + offset commit inside a transaction.

Q48. Difference between high-level and low-level consumers?
High-level (group management), low-level (manual partition assignment).

Q49. How does consumer handle large messages?
Increase fetch.max.bytes.

Q50. What is fetch.max.wait.ms?
Time to wait for enough data before returning poll.

Chapter 4: Kafka Storage & Retention (Q51–Q65)

Q51. How does Kafka store data on disk?
Log segments, append-only.

Q52. What is log segment in Kafka?
File chunk of a partition.

Q53. What is log compaction?
Keeps latest message per key, deletes old versions.

Q54. What is log.retention.ms?
Controls data expiry time.

Q55. What is log.retention.bytes?
Controls max size of topic logs.

Q56. How does Kafka clean up old messages?
Delete policy or compact policy.

Q57. What is retention.ms vs retention.bytes priority?
Whichever is reached first triggers cleanup.

Q58. What happens if disk is full on a broker?
Producer requests rejected, cluster instability.

Q59. How does Kafka achieve durability with fsync?
Configurable flush intervals.

Q60. What is log.cleaner.enable?
Controls background compaction.

Q61. What happens when broker crashes during write?
Messages persisted if committed to log + replicated.

Q62. What is index file in Kafka log segment?
Maps offset to file position for fast lookups.

Q63. How does Kafka achieve zero-copy transfer?
sendfile() system call.

Q64. Why is sequential I/O critical in Kafka?
Minimizes disk seek → high throughput.

Q65. How do you handle large message retention in Kafka?
Externalize to blob storage, keep references in Kafka.

Chapter 5: Kafka Replication & HA (Q66–Q80)

Q66. How does Kafka replicate partitions?
Leader–follower model.

Q67. What is unclean leader election?
Electing an out-of-sync replica as leader (data loss risk).

Q68. What is min.insync.replicas?
Minimum replicas required for a write to succeed.

Q69. What is controlled shutdown in Kafka?
Graceful broker shutdown, leader reassignment.

Q70. What is rack awareness?
Distributes replicas across racks for fault tolerance.

Q71. What is under-replicated partition (URP)?
Replica count < replication factor.

Q72. How do you monitor URPs?
JMX metric UnderReplicatedPartitions.

Q73. How does ISR shrink?
When follower falls behind leader.

Q74. How does leader election happen in Kafka?
Controller chooses ISR member.

Q75. How do you achieve multi-datacenter replication?
MirrorMaker 2, Confluent Replicator.

Q76. What is cross-cluster replication latency issue?
Network RTT + async replication → eventual consistency.

Q77. What is follower fetching in Kafka?
Followers pull data from leader.

Q78. What happens when leader broker fails?
ISR member becomes leader.

Q79. How to avoid data loss during broker crash?
Disable unclean election, use replication factor ≥ 3.

Q80. How does Kafka guarantee consistency with replicas?
Leader–follower replication + ISR enforcement.

Chapter 6: Kafka Streams & Connect (Q81–Q90)

Q81. What is Kafka Streams API?
Library for processing data in Kafka.

Q82. Difference between Kafka Streams and Spark Streaming?

Streams → lightweight, embedded.

Spark → cluster-based, heavy.

Q83. What is a KTable?
Stateful table representation of a stream.

Q84. What is changelog topic in Kafka Streams?
Stores state store updates.

Q85. How does Kafka Streams achieve fault tolerance?
State recovery from changelog.

Q86. What is Kafka Connect?
Framework for external system integration.

Q87. What is a Source vs Sink connector?
Source imports → Kafka, Sink exports → system.

Q88. How do you scale Kafka Connect?
By increasing tasks per connector.

Q89. What is Single Message Transform (SMT) in Kafka Connect?
Lightweight transformation before writing to Kafka or sink.

Q90. Common Kafka Connect challenges in production?
Connector imbalance, schema evolution, backpressure.

Chapter 7: Security, Monitoring & Ops (Q91–Q105)

Q91. What are Kafka authentication mechanisms?
SASL/PLAIN, SASL/SCRAM, Kerberos, SSL.

Q92. What are Kafka authorization mechanisms?
ACLs, RBAC (Confluent).

Q93. How do you encrypt data in Kafka?
TLS for transit, disk encryption for storage.

Q94. How do you enable multi-tenant Kafka cluster?
ACLs, quotas, namespace separation.

Q95. What is quota enforcement in Kafka?
Limits producer/consumer throughput.

Q96. How do you monitor Kafka cluster health?
JMX, Prometheus, Grafana.

Q97. What are critical Kafka metrics to monitor?
Consumer lag, URPs, ISR count, request latency.

Q98. How do you troubleshoot consumer lag?
Check slow processing, insufficient partitions, GC pauses.

Q99. How do you troubleshoot high broker CPU usage?
Compression overhead, GC, many small partitions.

Q100. What is Kafka’s impact of large messages?
Broker memory pressure, replication slowness.

Q101. How do you tune Kafka for low latency?
Lower linger.ms, smaller batch size, faster disks.

Q102. How do you tune Kafka for high throughput?
Higher batch size, compression, async commits.

Q103. What is dead letter queue in Kafka?
Special topic for failed messages.

Q104. How do you implement retries with Kafka?
Retry topic pattern (exponential backoff).

Q105. How do you test Kafka resiliency?
Chaos engineering: kill brokers, simulate network partitions.

Chapter 8: Banking & Real-World Use Cases (Q106–Q115)

Q106. How is Kafka used in trading systems?
Order routing, trade matching, risk monitoring.

Q107. How is Kafka used in payments?
Real-time settlement, fraud detection, audit trail.

Q108. How do you design Kafka for regulatory compliance?
No data loss, audit logs, DLQs, encryption.

Q109. How do you integrate Kafka with IBM MQ?
Kafka Connect MQ connectors, custom bridges.

Q110. How do you ensure exactly-once settlement in Kafka pipelines?
Transactions + idempotent DB writes.

Q111. How do you design Kafka for fraud detection?
Stream processing + anomaly detection + DLQ.

Q112. How do you ensure ordering of payment events in Kafka?
Partition by accountId/paymentId.

Q113. How do you scale Kafka for 1M+ TPS in banking?
High partition count, SSDs, tuned network, multiple brokers.

Q114. How do you integrate Kafka with batch systems in banks?
Kafka Connect + ETL → Data Lake.

Q115. What Kafka challenges are unique to finance?

Zero data loss.

Strict SLAs.

Compliance (audit, retention).

✅ That’s 115 Kafka Interview Questions with Answers, structured like a prep-book.

Would you like me to now create a PDF-style “Kafka Interview Book” (chapters, formatted explanations, diagrams) so you have a ready study guide?

You said:
prepare a scenario-based problem-solving section (e.g., “Consumer lag keeps increasing, how do you resolve?” Cover more such type of questions
ChatGPT said:

Got it 👍 You want a scenario-based Kafka interview prep section, like real-world production firefighting or design decisions, which senior engineers/architects face. I’ll create a dedicated list of 40+ scenario-driven, problem-solving Kafka interview questions with detailed answers — the kind you’d get in tough senior-level interviews at banks, fintechs, and product companies.

🔥 Kafka Scenario-Based Interview Questions & Answers
1. Consumer Lag Keeps Increasing. How Do You Resolve It?

Answer:

Check Consumer Throughput: Is consumer processing slower than message production? Profile the consumer logic (heavy DB calls, slow deserialization, network delays).

Parallelism: Increase consumer instances or partitions. Each consumer in a group can read from one partition at a time.

Batch & Async Processing: Enable batch consumption (max.poll.records), async commit, or move heavy processing to async worker threads.

Backpressure Handling: Use Kafka Streams/Kafka Connect with retries, DLQ, or scaling out.

Broker Health: Check broker disk/network IO, ISR shrinkage, or controller failovers.

2. Producers Report TimeoutException: Failed to update metadata after 60000 ms. What Do You Check?

Answer:

Cause: Producer can’t fetch metadata from brokers.

Debug Path:

Check broker availability (Zookeeper/KRaft controller).

Verify advertised listeners (producer may be connecting to wrong host:port).

Ensure correct security configs (SSL/SASL).

Check broker load — if request queue is overloaded.

Resolution: Fix advertised.listeners, check firewall/DNS, ensure brokers are up.

3. Messages Are Duplicated in Consumer Output. What Could Be Wrong?

Answer:

Consumer side: If enable.auto.commit=true, crashes before commit can cause duplicates.

Producer side: Retries + no idempotence can cause duplicates.

Fix:

Enable idempotent producer (enable.idempotence=true).

Use exactly-once semantics (EOS) in Kafka Streams/transactions.

Handle deduplication downstream if required.

4. Your Kafka Cluster Suddenly Has Many Under-Replicated Partitions (URPs). What Do You Do?

Answer:

Possible Causes:

Broker crash / broker GC pause.

Slow disk/network on one broker.

ISR flapping due to controller churn.

Resolution Steps:

Run kafka-topics --describe to identify affected brokers.

Check broker logs for throttling or disk full.

If a broker is dead, bring it back or reassign partitions.

For long-term fix → enable rack-awareness, replication factor ≥3, monitor ISR.

5. A Topic With 50 Partitions Suddenly Shows High Producer Latency. What Could Be Happening?

Answer:

Too many partitions on a single broker → metadata fetch overhead.

Partition skew (hot partition with all writes).

Small batch.size/linger.ms causing too many network calls.

Check CPU/network/disk saturation.

Fix by: increasing linger.ms, batch.size, ensuring partition key distribution, or expanding cluster.

6. Consumer Group Rebalances Frequently. What Could Be Wrong?

Answer:

Possible Causes:

Consumer crashes or takes too long to poll() (triggering session timeout).

max.poll.interval.ms too low compared to processing time.

Adding/removing consumers frequently (autoscaling).

Fix:

Increase max.poll.interval.ms.

Use cooperative sticky assignor.

Offload heavy processing out of consumer loop.

7. You See “Offset Commit Failed” in Consumer Logs. Why?

Answer:

Consumer may have left group during commit.

Broker not available or consumer group metadata unavailable.

Offset retention expired in broker (offsets.retention.minutes).

Solution: Tune commit frequency, monitor consumer liveness, increase retention.

8. How Do You Handle a Use Case Where One Event Must Trigger Updates in Multiple Microservices?

Answer:

Use fan-out via Kafka topics: one producer → multiple consumer groups (each service in its own group).

Ensure idempotency in consumers (to avoid double side-effects).

For ordering, keep partition key consistent.

For transactional integrity across services, use Saga pattern with Kafka events.

9. Broker Disk Usage Suddenly Spikes. What’s Happening?

Answer:

Log cleanup not happening → retention misconfigured.

Compaction disabled on compacted topics.

Consumers are offline (retention ensures messages are stored longer).

Producer spamming huge messages (check max.message.bytes).

Fix: tune log.retention.*, enable compaction where needed, scale disk, monitor producers.

10. A Consumer Needs Messages in Strict Order Across All Partitions. How Do You Handle?

Answer:

Kafka guarantees ordering only within a partition.

To enforce global order:

Use single partition topic (but loses scalability).

Or implement sequencing at application level using a central ordering service.

Typically solved via design tradeoff: scale vs order.

11. How Do You Ensure No Data Loss in Kafka?

Answer:

Producer:

acks=all

retries=Integer.MAX_VALUE

enable.idempotence=true

Broker:

replication.factor ≥ 3

min.insync.replicas = 2

Consumer:

Use manual commits (after processing).

Cluster:

Rack awareness, monitoring ISR.

12. A Consumer is Slower Than Others in Same Group. What Happens?

Answer:

Kafka assigns one partition per consumer. Slow consumer → its partition lags.

Other consumers cannot take over unless it leaves group.

Fix: scale partitions/consumers, fix slow processing, use async worker pool per consumer.

13. You’re Migrating Kafka Topics to a New Cluster With Zero Downtime. How?

Answer:

Use MirrorMaker 2.0 for cross-cluster replication.

Cut over consumers gradually by repointing them.

Ensure offset sync using MirrorMaker offset translation.

Validate lag, then decommission old cluster.

14. What If Messages Are Too Large for Kafka Broker to Handle?

Answer:

Kafka default max.message.bytes = 1MB.

If messages exceed this:

Increase broker & topic-level max.message.bytes.

Or split large payload into chunks.

Or store payload in object storage (S3/HDFS) and pass reference in Kafka.

15. High CPU Usage on Broker. What’s the Checklist?

Answer:

Check GC pauses (tune heap, G1GC/ZGC).

Too many partitions per broker.

Compression/decompression overhead (optimize producer compression).

Large number of clients frequently fetching metadata.

Solution: expand cluster, rebalance partitions, tune GC, enable client-side caching of metadata.

16. You Have Hot Partition Issue (One Partition Receives Most Traffic). Fix?

Answer:

Likely bad partition key.

Use round-robin partitioner or hashing with better key distribution.

Use custom partitioner.

Add partitions (but note ordering semantics may break).

17. A Downstream System Cannot Keep Up With Kafka Consumer Throughput. How Do You Protect It?

Answer:

Add buffering (Kafka Streams, Akka, in-memory queue).

Apply rate limiting / backpressure.

Use Dead Letter Queue for retries.

Scale downstream system or batch writes.

18. What If Offset Data in Kafka Is Lost (Compacted)?

Answer:

Offsets stored in __consumer_offsets topic.

If retention expired → consumer loses progress.

Solution: increase offsets.retention.minutes.

Alternatively, store checkpoint externally (DB, Redis).

19. How Do You Handle Multi-Region Kafka Deployment?

Answer:

Options:

Active-Active: MirrorMaker 2.0 with bidirectional sync (conflict resolution required).

Active-Passive: Replicate logs one way, failover on disaster.

Tradeoff: Latency vs consistency.

Must tune replication flow carefully to avoid loops.

20. How Do You Debug Message Loss in Production?

Answer:

Verify producer configs (acks, retries, idempotence).

Check ISR count at time of publish.

Check consumer lag (maybe consumed but not processed).

Check broker logs for unclean leader election.

Use audit pipeline: compare source vs sink counts.

👉 I can extend this into a full 40–50 scenario set, covering:

Security Issues (SASL/SSL misconfigs, Kerberos issues)

Kafka Streams failures

DLQ, retries, poison-pill handling

Schema evolution & compatibility

ZooKeeper → KRaft migration issues

Chaos engineering scenarios (broker crash, network partition)

Do you want me to expand this to a complete “Kafka Troubleshooting Playbook” (40–50 detailed scenarios) in one go (like a dedicated prep book), or should I build it in progressive chunks (10–15 scenarios at a time) so it’s easier to digest?

You said:
 “Payments messages are duplicated across DB and Kafka, how do you fix?”)
ChatGPT said:

Perfect 👌 That’s exactly the type of production-grade scenario banks/fintech interviews love to ask. Let’s take your example:

Scenario: “Payments messages are duplicated across DB and Kafka, how do you fix?”
🔹 Problem

Payments are critical transactions — duplication = double debit/credit.

Messages flow: Payment Service → Kafka → Consumer → DB (or DB first, then Kafka).

But duplicates are observed either in Kafka topic or DB records.

🔹 Root Causes to Check

Producer-side duplicates

Producer retries without idempotence.

Network partition → message resent.

Consumer-side duplicates

Consumer crashes after DB insert but before offset commit → replay on restart.

Multiple consumer groups reading same topic unintentionally.

Application-side issues

No deduplication logic in DB schema (e.g., missing unique constraint on payment_id).

Downstream connectors (e.g., Kafka Connect → DB) retried transactions without idempotency.

🔹 Possible Solutions
✅ 1. Enable Idempotence in Producer

Set:

enable.idempotence=true
acks=all
retries=Integer.MAX_VALUE


Guarantees no duplicate messages at Kafka broker level.

✅ 2. Use Transactions for Exactly-Once Semantics (EOS)

Kafka supports atomic writes across Kafka + DB (via consumers).

Pattern:

Start a transaction.

Consume record → insert into DB → commit Kafka offset in same transaction.

Commit transaction.

If crash → nothing is committed → no duplicates.

Example (Spring Kafka):

factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
factory.getContainerProperties().setTransactionManager(kafkaTransactionManager);

✅ 3. Idempotent DB Writes

Enforce business key uniqueness (payment_id) at DB layer.

If duplicate arrives → DB rejects it.

Combine with upsert logic:

INSERT INTO payments (payment_id, amount, status)
VALUES (?, ?, ?)
ON CONFLICT (payment_id) DO NOTHING;

✅ 4. Outbox Pattern (DB → Kafka Consistency)

Instead of writing to DB + Kafka separately (risk of mismatch):

Service writes only to DB (in local transaction).

DB table has an “outbox” entry.

A background job / Kafka Connect reads outbox and publishes to Kafka.

Ensures exactly-once message delivery aligned with DB state.

✅ 5. Deduplication at Consumer

Maintain a dedup cache (Redis, DB, Kafka state store).

Use payment_id as key → skip processing if already seen.

Works well for downstream consumers where DB constraint isn’t enough.

🔹 Production-Proven Fix (What Banks Use)

Outbox + Idempotent Consumer + DB Constraint

DB is always source of truth.

Kafka mirrors DB state (outbox).

Consumer dedup + DB uniqueness ensures no double debit/credit.

Combine with EOS transactions if running Kafka Streams or Spring Kafka.

🔹 Answer (Interview Style)

"Payments duplication typically occurs due to retries, lack of idempotence, or DB/Kafka dual-write issues. In production, I’d fix this by enabling idempotence on the producer, enforcing uniqueness at the DB layer with payment_id, and adopting the Outbox pattern to ensure DB and Kafka are consistent. If strict exactly-once semantics are required, I’d also use Kafka transactions so that DB updates and offset commits happen atomically. This guarantees no double-processing of payment messages even during retries or crashes."

🔥 Do you want me to build a full dedicated section of 20–25 “Payments/Banking Kafka Scenarios” like this (duplication, retries, ordering, settlement mismatches, DLQ for failed payments, cut-off batch processing, multi-region replication for payments)?

That would make your prep bank-specific and interview-cracking level.

You said:
yes
ChatGPT said:

Perfect 🙌
Let’s build a dedicated section: “Kafka in Banking/Payments – 20+ Real Scenarios with Solutions”.
These are exactly the kind of problem-solving questions banks (Citi, JP Morgan, Barclays, UBS) ask when hiring senior engineers/VPs.

🔥 Kafka in Payments/Banking – Scenario-Based Q&A
1. Payments messages are duplicated across DB and Kafka. How do you fix?

✅ Fix:

Enable idempotent producers (enable.idempotence=true).

Enforce unique payment_id constraint in DB.

Use Outbox Pattern (write once to DB, stream to Kafka).

Optionally, use EOS transactions (Kafka offset commit + DB insert atomic).

2. A Payment Transaction is Stored in DB but Not Published to Kafka. How Do You Prevent Inconsistency?

✅ Fix:

Avoid dual writes (DB + Kafka separately).

Implement Outbox Pattern → DB transaction ensures payment + outbox entry.

Kafka Connect/CDC reads outbox → publish to Kafka.

Guarantees DB and Kafka are always consistent.

3. Payment Consumer Processes the Same Message Twice (Double Debit). How Do You Handle?

✅ Fix:

Consumer idempotence:

Upsert in DB (INSERT … ON CONFLICT DO NOTHING).

Maintain processed_payment_ids in Redis/DB.

Enable EOS in Kafka Streams/Spring Kafka → atomic processing.

DLQ for poison-pill messages.

4. Cut-off Time Payments Must Be Batched at 5 PM Daily. How to Design in Kafka?

✅ Fix:

Use Kafka Streams windowing (tumbling window = 24h, close at 5 PM).

Or schedule a batch job (Quartz, Airflow) → consumes payments till 5 PM, publishes an aggregated "EOD Payment File" to Kafka.

Store intermediate state in RocksDB / DB.

5. Payment Settlement Service is Lagging. How to Avoid Delays?

✅ Fix:

Check consumer lag → increase partitions, scale consumers.

Use async workers inside consumer.

Apply backpressure + DLQ for failed payments.

Profile DB writes (batch inserts instead of row-by-row).

6. FX Payment Requires Multi-Step Workflow (Debit, FX Conversion, Credit). How Do You Ensure Consistency?

✅ Fix:

Implement Saga pattern using Kafka.

Each service emits events:

Debit → FX → Credit.

If one step fails → compensating transaction (e.g., reverse debit).

Store state machine in Kafka Streams.

7. Same Payment Message Processed by Multiple Services (Fraud Check, AML, Settlement). How Do You Handle?

✅ Fix:

Fan-out with multiple consumer groups → each service gets all events.

Add DLQ per service for failures.

Use Schema Registry + versioning to avoid breaking services.

8. Payment Messages Require Strict Ordering. How Do You Ensure?

✅ Fix:

Use partition key = payment_id/account_id.

This ensures all events for same payment/customer go to one partition → strict ordering preserved.

If global ordering required → use single partition (scalability trade-off).

9. Payment Service Produces High Volume (100k TPS) → Broker Latency Increases. How Do You Scale?

✅ Fix:

Tune producer:

linger.ms=5, batch.size=64KB, compression.type=snappy.

Spread partitions across brokers.

Add brokers (rebalance partitions).

Optimize disk → SSDs, increase page cache.

10. Bank Audit Requires 7 Years of Payment Records. How Do You Store in Kafka?

✅ Fix:

Kafka not ideal for very long-term storage.

Approach:

Kafka retention = 30 days.

Stream older events to HDFS/S3 via Kafka Connect → store for 7 years.

Query via Presto/Trino.

11. AML (Anti-Money Laundering) Engine Needs Real-Time Payment Alerts. How Do You Build?

✅ Fix:

Use Kafka Streams → consume payments, join with customer data, run fraud rules in real-time.

Emit suspicious events to AML Alert topic.

Integrate with monitoring/dashboard.

12. Payment Messages Must Be Masked Before Sending to Downstream Services. How?

✅ Fix:

Implement Kafka Connect SMT (Single Message Transform) to mask sensitive fields.

Or, Kafka Streams pipeline → mask PAN/SSN before publishing downstream.

Use encryption for sensitive payloads.

13. Cross-Border Payments Require Multi-Region Kafka. How Do You Ensure Reliability?

✅ Fix:

Use MirrorMaker 2.0 to replicate topics between regions.

Active-passive: one cluster writes, replicate to DR.

Active-active: careful conflict resolution (payment_id key).

Ensure idempotency + deduplication in multi-region setup.

14. Payment Replay Is Required for Reconciliation. How Do You Design This?

✅ Fix:

Keep raw payment events in a compact/retained Kafka topic.

Consumers can reset offsets (--from-beginning) to replay payments.

For reconciliation jobs, consume snapshot + reapply transactions.

15. What If Payment Consumer Crashes Mid-Processing (after DB write, before offset commit)?

✅ Fix:

Exactly-once with transactions → offset commit & DB update atomic.

Or enforce idempotency at DB level (payment_id uniqueness).

Upon restart → duplicate ignored.

16. Payments Fail Due to Schema Evolution (Producer Added a Field). How Do You Handle?

✅ Fix:

Use Confluent Schema Registry with BACKWARD compatibility.

Consumers ignore unknown fields → no crash.

Rolling upgrades possible.

17. Large Payment Batch Messages Fail Due to Kafka Message Size. What Do You Do?

✅ Fix:

Kafka default max.message.bytes=1MB.

Solutions:

Increase broker/topic max message size.

Split batch into smaller chunks.

Store bulk payload in S3, send reference ID via Kafka.

18. Duplicate Settlement Instructions Are Received from External Systems. How Do You Handle?

✅ Fix:

Deduplication by payment reference ID.

Consumer maintains state (Kafka Streams state store, Redis, DB).

Ignore duplicate keys.

19. Kafka Lag Suddenly Spikes at Market Close When Millions of Payments Arrive. How Do You Scale?

✅ Fix:

Pre-warm cluster with enough partitions/consumers.

Enable elastic scaling (K8s autoscaler for consumers).

Use batch inserts in DB (instead of row-by-row).

Spread load by partitioning by account/customer.

20. How Do You Handle Regulatory Reporting of Failed/Delayed Payments?

✅ Fix:

Failed messages → DLQ (dedicated Kafka topic).

Build reporting service consuming DLQ, generating reports.

Use Kafka Connect → ElasticSearch/Splunk for dashboards.

21. Payments in Kafka Must Never Be Lost Even During Broker Crash. What’s Your Config?

✅ Fix:

Producer: acks=all, enable.idempotence=true, retries=Integer.MAX_VALUE.

Broker: replication.factor=3, min.insync.replicas=2, rack-awareness enabled.

Consumers: commit offsets only after successful processing.

22. How Do You Detect Payment Message “Poison Pills” (Always Failing Processing)?

✅ Fix:

Retry a limited number of times.

If still failing → send to DLQ topic.

Monitor DLQ for manual resolution.

Prevent entire consumer group from stalling.

23. Payment Producers Send Duplicate Records After Network Timeout. How Do You Ensure Exactly-Once?

✅ Fix:

Producer configs:

enable.idempotence=true

acks=all

Use Kafka Transactions if writing to multiple partitions/topics.

24. How Do You Guarantee Ordering for Debit/Credit of Same Account but Scale Across Many Accounts?

✅ Fix:

Partition key = account_id.

Ensures ordering for same account.

Accounts are spread across partitions → parallelism.

25. A Compliance Officer Requests “Replay All Payments from Last Month for Audit”. How Do You Achieve This?

✅ Fix:

If topic retention < 30 days → also store to HDFS/S3 via Kafka Connect.

Use offset reset tool or new consumer group to reprocess events.

For long-term, maintain immutable payment log archive outside Kafka.

26. Trade matching events arrive out of order — settlements break. How do you fix?

Cause: Events split across partitions or multiple sources with different latencies.
Fix: Partition by tradeId so all events for a trade land on same partition; use sequence numbers per trade and check ordering in consumer; buffer and wait-for-missing-sequence within a bounded window; use Kafka Streams state store for per-trade reassembly.
Prod tip: Enforce upstream system to emit tradeId and sequenceNo. Add monitoring for reordered events and thresholds for human intervention.

27. End-of-day (EOD) settlement file must be built exactly once — intermittent duplicates appear.

Cause: Dual-processing or retries; offset commit after file write not atomic.
Fix: Generate EOD file after consumer verifies all partitions processed up to an offset watermark; use transactional writes (Kafka transaction or DB transaction + outbox) or write file to immutable storage and record its checksum/metadata in DB with unique key before publishing completion event.
Prod tip: Use idempotent file naming (date + job-run-id) and store manifest in a reliable DB.

28. Real-time risk analytics needs low-latency market data joins with trades; joins are missing late-arriving market ticks.

Cause: Windowing too small; late events beyond watermark dropped.
Fix: Increase allowed lateness and use tumbling/sliding windows with grace period; keep changelog for late arrivals; implement event-time processing with watermarking.
Prod tip: Balance lateness tolerance vs state size; persist state to RocksDB and scale stateful processors horizontally.

29. Massive reconciliation mismatch between ledger DB and Kafka-derived positions. How to debug and fix?

Cause: Reprocessing logic divergent, missing events, faulty enrichment, or duplicate consumption.
Fix: Re-run replay from authoritative offset, compare hashes/checksums of intermediate aggregates, enable end-to-end tracing (trace-id across systems), reconcile counts per partition, use compacted topics for canonical state.
Prod tip: Keep immutable event archive (S3) and a reconciliation microservice that compares snapshots.

30. Regulatory report needs all rejected trade records within 24 hours but DLQ contains partial data.

Cause: DLQ ingestion inconsistent, missing metadata (rejection reason, timestamps).
Fix: Standardize failure handling — always publish failed record to DLQ with enriched metadata. Make DLQ schema enforced via Schema Registry. Build a scheduled job to aggregate DLQ and produce regulatory report.
Prod tip: Protect DLQ retention and backup to object storage for forensic audits.

31. High-frequency trading (HFT) consumers need <1ms latency; Kafka tail latency spikes.

Cause: JVM GC pauses, large message batches, disk I/O, network jitter.
Fix: Use tuned JVM (low pause GC), dedicate brokers for low-latency topics, reduce linger.ms and batch.size, use faster NICs and NVMe SSDs, minimize client-side serialization overhead (e.g., use binary formats).
Prod tip: Consider using in-memory stores or specialized messaging for ultra-low-latency segments; reserve Kafka for near-real-time where ms-level SLAs are acceptable.

32. Trade lifecycle requires atomic update across multiple services; two-phase commit not feasible. How to coordinate?

Cause: Multiple microservices with independent datastores.
Fix: Implement Saga pattern with compensating transactions coordinated by events in Kafka; use durable saga state (Kafka Streams or DB) and ensure idempotent compensating actions.
Prod tip: Provide observability on saga progress; timeout and manual compensation processes.

33. Market data spikes cause broker overload and price feed consumers drop. How to protect critical consumers?

Cause: Burst traffic overwhelms bandwidth and IO.
Fix: Apply broker-level quotas for non-critical producers, use topic-level throttling, provision high-throughput brokers for market-data topics, and implement priority consumer groups for critical services.
Prod tip: Use separate clusters for market data vs business-critical transactional topics to isolate impact.

34. Schema drift in trade messages breaks downstream analytic jobs. How to manage evolving schemas?

Cause: Producers change schemas without compatibility guarantees.
Fix: Use Schema Registry with enforced compatibility modes (BACKWARD / FORWARD / FULL); run contract tests; introduce fields as optional, not remove required fields.
Prod tip: Automate schema validation in CI and have a staged rollout of new schemas with canary consumers.

35. Settlement cycle fails when a broker upgrade causes leader churn. How to minimize impact?

Cause: Rolling upgrade triggers leadership moves, causing brief unavailability.
Fix: Use controlled rolling upgrades, pre-move leadership off a broker, ensure replication factor and ISR healthy before upgrade, enable controlled.shutdown.
Prod tip: Upgrade during low-volume windows and monitor URP metrics in real time.

36. Latency spikes caused by compaction tasks on compacted topics used for account state.

Cause: Log cleaner consumes CPU/disk affecting I/O.
Fix: Tune log.cleaner.threads, log.cleaner.io.max.bytes.per.second, and separate compacted topics onto dedicated disks/brokers.
Prod tip: Schedule heavy compaction during off-peak hours for large state topics.

37. Cross-system ID mapping (external trade IDs ↔ internal IDs) inconsistent across services.

Cause: Mapping updates not propagated or race conditions.
Fix: Centralize mapping in a compacted Kafka topic as the single source of truth; services read mapping and cache locally; use changelog for updates.
Prod tip: Use versioned mapping and include effective timestamps for rollbacks.

38. Analytics models consume delayed features due to late enrichment jobs, model outputs stale.

Cause: Feature enrichment pipeline slower than model consumption.
Fix: Add staging topics for raw features and enrichment events; use stream-table joins with change logs and retain enrichment history; fallback to last-known-good feature if enrichment missing.
Prod tip: Add SLA metrics per feature and alerts when feature freshness drops.

39. Regulatory audit requires tamper-proof audit trail of trade events. How to provide it?

Cause: Need immutable, provable logs.
Fix: Keep append-only Kafka topics with retention per regulation; replicate to immutable object storage (S3 with WORM/immutable buckets), compute and store checksums, sign batches. Use Kafka’s immutability + external archival for long-term.
Prod tip: Maintain provenance metadata and digital signatures for legal admissibility.

40. Massive consumer lag at month-end reporting job. How to prioritize critical reporting consumers?

Cause: Batch spikes and many competing consumers.
Fix: Apply consumer quotas and dedicate faster consumers/instances for reporting; create a high-priority topic copy via MirrorMaker to isolate reporting load.
Prod tip: Build a backpressure-aware ingestion pipeline that can throttle non-critical producers temporarily.

41. Stateful Kafka Streams job needs migration to bigger cluster without state loss. How to perform?

Cause: Need state store relocation while preserving correctness.
Fix: Use Kafka Streams’ built-in repartitioning and state store changelog topics; perform rolling upgrades with state restoration from changelog topics; use interactive queries to drain traffic gradually.
Prod tip: Pre-provision target cluster and validate state restoration under test before cutover.

42. Regulatory ‘right to be forgotten’ (GDPR) conflicts with immutable audit logs. How to reconcile?

Cause: Regulations demand deletion; audit logs are append-only.
Fix: Use tokenization/pseudonymization: replace PII with irreversible hashes in event stream while keeping audit metadata; maintain separate secure subject-access logs to satisfy requests; archive original PII in controlled vault and delete per policy.
Prod tip: Consult legal/regulatory and design data minimization and encryption-at-rest with key rotation to limit exposure.

43. Trade enrichment service is a single point of failure causing processing backlog. How to make it resilient?

Cause: Central enrichment service downtime slows pipeline.
Fix: Cache enrichment data in a compacted Kafka topic and colocate enrichment lookup in consumers (local cache backed by changelog); make enrichment service horizontally scalable and idempotent.
Prod tip: Keep enrichment data compact, TTL evictions, and health-check-based routing to healthy instances.

44. Model-serving pipeline using Kafka has drifting feature distributions unnoticed (model staleness). How to detect and act?

Cause: No monitoring for feature distribution shifts.
Fix: Stream feature statistics (means, histograms) to a monitoring topic; run drift detectors and generate alerts; automatically trigger model retraining pipelines.
Prod tip: Keep sliding-window feature metrics and automate retraining threshold triggers.

45. Broker disk failure requires emergency failover — risk of split-brain writes in active-active replication.

Cause: Multi-region active-active with unsafeguarded writes.
Fix: Prefer active-passive for sensitive financial writes or implement conflict resolution logic (last-writer-wins with causal ordering or payment-id based dedup). Use MirrorMaker 2 with careful topology to avoid write loops.
Prod tip: Run regular failover drills and verify idempotency/dedup safeguards.

46. Regulatory reporting needs lineage of derived fields (how was margin calculated?). How to provide provenance?

Cause: Derived computations lack traceability.
Fix: Emit metadata with each derived event pointing to source event offsets/IDs and transformation version; use immutable changelog topics for intermediate stages. Provide a lineage service that can traverse upstream offsets to recreate derivations.
Prod tip: Store transform code version and parameter snapshots along with derived events.

47. Core banking system cannot accept replay storm when Kafka consumers reset offsets for debugging. How to prevent overload?

Cause: Replay replays millions of messages causing DB overload.
Fix: Implement replay rate limiting, consumer-side throttling, and batch writes on consumer; use a staging pipeline to rehydrate state without hitting core in real-time.
Prod tip: Add circuit breakers and replay quotas per business unit.

48. Cross-asset portfolio aggregation requires joins across very large topics — state store grows beyond capacity.

Cause: Stateful joins with high cardinality keys.
Fix: Pre-aggregate upstream where possible; shard joins by time windows or by customer segments; offload large state to scalable external stores (Cassandra/Redis) and use compacted changelogs for snapshots.
Prod tip: Re-evaluate windowing strategy and use incremental aggregation to reduce state size.

49. FIX gateway publishes malformed messages causing downstream consumer crashes. How to contain?

Cause: Bad producer code or unexpected message variants.
Fix: Introduce a validation/normalization layer near the producer (parsing and schema validation), reject or route malformed messages to a quarantine topic for inspection; enforce Schema Registry checks.
Prod tip: Use SMTs in Kafka Connect to sanitize inputs and keep strict schema evolution rules.

50. Back-pressure from a slow settlement DB causes producer timeouts and data loss. How to design backpressure handling end-to-end?

Cause: Downstream slowness cascades to upstream.
Fix: Implement buffer queues, separate ingestion and settlement pipelines, use DLQs and retry topics with exponential backoff, apply quotas upstream, and scale settlement DB or use write-batching.
Prod tip: Build health signals that can throttle upstream producers automatically when settlement lag is detected.

51. Large-scale reprocessing required for a bug fix — how to replay while maintaining derived state correctness?

Cause: Stateful processors will produce different outputs for older events.
Fix: Version your processing logic and create a controlled replay pipeline: spin up a fresh processing cluster with corrected code and restore state from changelog topics or start from earliest offsets into a new output topic. Compare new output with previous and promote after verification.
Prod tip: Use canary replays on subsets of data before full-scale reprocessing.

52. Real-time exposure calculations must include late events up to 2 hours after trade. How do you handle?

Cause: Need to include late-arriving corrections.
Fix: Use stream processing with event-time windows and a large grace period; keep changelog topics for state to allow corrections to update computed exposure; design idempotent updates for exposure re-calculation.
Prod tip: Alert when corrections outside the expected window occur and provide reconciliation reports.

53. Multiple teams share a Kafka cluster and noisy tenants affect others. How to enforce multi-tenancy fairness?

Cause: One tenant floods cluster resources.
Fix: Enforce quotas (producer_byte_rate, consumer_byte_rate), use separate topics/clusters per tenant for strict isolation, and set ACLs + quotas. Monitor per-tenant metrics.
Prod tip: Offer self-service topic provisioning with limits and enforce tagging for billing.

54. Trade confirmations must be compensated on failure (compensation workflows fail). How to improve reliability?

Cause: Missing retry logic and state reconciliation.
Fix: Implement idempotent confirmation endpoints, create compensation events for reversals, persist saga state and retry logic in Kafka Streams or DB, and provide manual resolution path.
Prod tip: Keep compensation metrics and SLA dashboards.

55. Monthly regulatory deadline: export all trades with specific enrichment that requires joins; pipeline overwhelmed. What temporary & long-term fixes?

Cause: Bulk query and join spikes for long historical windows.
Fix (temp): Pause non-critical workloads, scale up consumer fleet, run batch jobs over archived data in data lake.
Fix (long-term): Pre-compute and store enriched snapshots daily in compacted topics or data lake; provide APIs for exports rather than real-time joins.
Prod tip: Automate capacity planning for known periodic spikes and pre-warm clusters.

If you want, I can now:

Convert these into a printable PDF “Banking Kafka Playbook” with diagrams and checklists, or

Expand any of the scenarios into step-by-step runbooks (commands, JMX metrics to check, sample configs), or

Create mock interview questions based on these scenarios with ideal answer bullet points.

Which would you prefer next?

You said:
focus deeper on Payments (100+ scenarios total) to make it like a mini “Payments + Kafka Troubleshooting Playbook”?
ChatGPT said:
Thought for 4 seconds
Payments + Kafka — Troubleshooting Playbook (100 practical scenarios)

Awesome — below is a compact, battle-tested Payments + Kafka troubleshooting playbook with 100 scenarios (P1–P100).
Each scenario has: Problem → Likely cause → Short fix → Production tip. This is designed as an interview-ready reference and an on-call checklist you can memorize or paste into runbooks.

How to use this

Scan the problem statement, match metrics/logs, then follow the short fix and prod tip.

If you want, I can expand any scenario into a full runbook (commands, JMX metrics, config snippets).

P1 — Duplicate payments in DB and Kafka

Cause: Dual-write or producer retries without idempotence.
Fix: Outbox pattern OR idempotent producer + DB unique constraint on payment_id.
Tip: Always have payment_id as business key and DB uniqueness.

P2 — Payment written to DB but not in Kafka

Cause: Dual-write race or failed Kafka publish after DB commit.
Fix: Outbox (write outbox in same DB tx → CDC/poller to Kafka).
Tip: Monitor outbox backlog and publisher errors.

P3 — Consumer processes payment twice (double debit)

Cause: Processed DB insert before offset commit and crash.
Fix: Transactional consumption (commit offset with DB update) or idempotent DB writes.
Tip: Use INSERT ... ON CONFLICT DO NOTHING for payments.

P4 — Producer TimeoutException when sending payments

Cause: Broker unreachable, wrong listeners, or network ACLs.
Fix: Check advertised.listeners, network/firewall, broker health.
Tip: Add producer-side retry/backoff with exponential backoff.

P5 — Payment ordering violated across services

Cause: Wrong partition key or multiple partitions for same payment.
Fix: Partition by payment_id or account_id requiring ordering.
Tip: Document partitioning scheme and enforce upstream keys.

P6 — Hot partition for major payer causing latency

Cause: All messages for big payer hashed to one partition.
Fix: Revisit key design — include sharding salt or composite key.
Tip: Avoid deterministic single-key hotspots for big clients.

P7 — Consumer lag spikes at cut-off time

Cause: Burst of messages; insufficient consumers or DB slowness.
Fix: Pre-scale consumers, batch DB writes, use async workers.
Tip: Pre-warm consumers ahead of scheduled spikes.

P8 — Poison-pill payment causing consumer crash

Cause: Malformed payload or unexpected schema.
Fix: Validate input; route invalid to quarantine DLQ with metadata.
Tip: Add schema validation at ingress (SMT or dedicated parser).

P9 — DLQ growing rapidly with payment failures

Cause: Downstream system unavailable or bad data flood.
Fix: Throttle retries, increase DLQ retention, fix root cause.
Tip: Create dashboards for DLQ volume + top error reasons.

P10 — Settlement file generated twice

Cause: Job ran twice due to retry or lack of idempotent file name.
Fix: Use atomic manifest entry in DB or unique EOD job idempotency check.
Tip: Include checksum and job-run-id in manifest for verification.

P11 — Payment latency high end-to-end

Cause: Large serialization, sync DB calls, small batch sizes.
Fix: Use binary serialization (Avro/Protobuf), async DB writes, tune batch/linger.
Tip: Measure p95 and p99 latencies at each hop.

P12 — Retention policy causing audit gaps

Cause: Topic retention too short for compliance.
Fix: Archive to S3/HDFS via Connect and keep minimal retention in Kafka.
Tip: Keep immutable archive with indexes for fast lookup.

P13 — Multi-region duplicate payment due to active-active writes

Cause: Bidirectional replication without dedup logic.
Fix: Use single-writer per key or global dedup with business key and idempotent processing.
Tip: Prefer active-passive for payments unless dedup is rock-solid.

P14 — Payments missing enrichment (e.g., FX rates)

Cause: Enricher service down or joining misconfigured.
Fix: Fallback to cached rates or mark payment as pending until enrichment available.
Tip: Keep compacted topic of enrichment data for local lookup.

P15 — Large payment batches exceed max.message.bytes

Cause: Batch message > broker/topic max.
Fix: Increase max.message.bytes or split batches; better: store payload in S3 and send reference.
Tip: Prefer chunking or references for large attachments.

P16 — Offset commit lost due to offsets topic cleanup

Cause: __consumer_offsets retention too low.
Fix: Increase offset retention or store consumer checkpoints externally.
Tip: For long offline consumers, use external checkpointing.

P17 — Payment reconciliation mismatches (counts differ)

Cause: Partial publishes, consumer crashes, or transform bugs.
Fix: Add end-to-end tracing (trace-id), compare counts per partition + time window.
Tip: Store message checksums to detect silent corruption.

P18 — Rate limiter kicks in for large corporate client

Cause: Single client floods cluster throughput.
Fix: Apply per-client quotas on producer or topic.
Tip: Implement client-tiered SLAs and quota enforcement.

P19 — Payment event schema change breaks consumers

Cause: Non-backward-compatible schema change.
Fix: Use Schema Registry with compatibility rules and staged rollout.
Tip: Add consumer-side tolerant parsing (ignore unknown fields).

P20 — Retry storms when downstream DB recovers

Cause: Many consumers retrying simultaneously after outage.
Fix: Exponential backoff and jitter in retry logic; use retry topics with delays.
Tip: Use a token bucket for controlled re-ingestion.

P21 — Payment correlation lost across events (traceability)

Cause: Missing correlation id in messages or inconsistent propagation.
Fix: Propagate a trace_id / payment_id across all services and logs.
Tip: Index trace_id in logs and Kafka headers for fast forensics.

P22 — Producers blocked because buffer.memory full

Cause: Broker slow to acknowledge, or producers burst.
Fix: Increase buffer.memory, tune max.block.ms, or slow producers.
Tip: Alert when producer buffer usage > 80%.

P23 — Broker under-replicated partitions causing risk of data loss

Cause: Broker failure or network issues.
Fix: Restore broker, reassign partitions, ensure replication factor >=3.
Tip: Monitor UnderReplicatedPartitions metric constantly.

P24 — Payment dedup cache growth causes OOM in consumers

Cause: Dedup store unbounded.
Fix: Use TTL, LRU cache, or external store (Redis) for dedup.
Tip: Use time-windowed deduplication and compacted store for longer-term.

P25 — Payments not delivered to downstream due to ACL issues

Cause: Misconfigured ACLs for service account.
Fix: Review and apply correct ACLs (produce/consume) for topics and groups.
Tip: Automate ACL provisioning with IaC.

P26 — Kafka Connect sink to DB duplicates during restarts

Cause: At-least-once semantics + connector retries.
Fix: Enable exactly-once connectors if available or add idempotent sink logic (upsert).
Tip: Use tombstones and key-based upserts in sink.

P27 — Payment audit trail fails to export nightly

Cause: Connector task failing or offset mismanagement.
Fix: Fix connector errors; replay offsets after fix; ensure checkpointing.
Tip: Alert on connector task failure immediately.

P28 — Consumer group keeps rebalancing during peak

Cause: max.poll.interval.ms too small or long processing inside poll.
Fix: Move heavy processing to separate threads and increase max.poll.interval.ms.
Tip: Use cooperative rebalance assignor to reduce disruption.

P29 — Payment processing stuck due to DB deadlock

Cause: Concurrent consumers causing conflicting DB locks.
Fix: Use idempotent writes, order-preserving partitioning, or single writer per account.
Tip: Use batch DB writes and optimistic concurrency control.

P30 — Delayed payments due to GC pauses on JVM consumers

Cause: Poor GC tuning or large heap with long pauses.
Fix: Tune GC (G1/ZGC), reduce heap, avoid large object creation.
Tip: Monitor GC pause metrics and set alerts for long pauses.

P31 — Kafka Streams job computing balances shows incorrect state after restart

Cause: State store restore incomplete or changelog inconsistencies.
Fix: Ensure changelog topics retained and topics healthy; validate restoration logs.
Tip: Keep changelog topics replicated and separate for quick recoveries.

P32 — Payment consumers skipping messages after crash

Cause: Auto-commit before processing.
Fix: Use manual commit after successful processing.
Tip: Use idempotent processing to be safe.

P33 — Settlement instructions delayed due to slow network between DCs

Cause: Cross-region replication latency.
Fix: Localize settlement-critical processing in same region or use sync replication with failover.
Tip: Use MirrorMaker for async replication and bounded-latency SLA.

P34 — Misrouted payment messages sent to wrong topic

Cause: Misconfiguration in producer topic name or router logic.
Fix: Add validation and topic ACL restrictions; implement naming conventions.
Tip: Add a policy service that validates topic targets before publish.

P35 — Payment metadata missing (timestamps or currency)

Cause: Upstream producer bug or schema contract not enforced.
Fix: Enforce schema registry validation and reject messages missing required fields.
Tip: Add mandatory header checks in ingress layer.

P36 — High producer CPU due to compression overhead

Cause: CPU-bound compression selection (gzip CPU heavy).
Fix: Use faster compression like LZ4 or Snappy.
Tip: Benchmark compression for your payload shapes.

P37 — Payment retry topics overflow disk

Cause: Exponential retry retention misconfigured or too many retries.
Fix: Limit retries, add TTL on retry topics, or move to DLQ after N retries.
Tip: Monitor size and retention of every retry topic.

P38 — Payment enrichment service returns inconsistent results after upgrade

Cause: Versioned enrichment logic or schema mismatch.
Fix: Run canary traffic, maintain backward compatible enrichers, and include version in events.
Tip: Keep enrichment logic stateless or store migration-safe mappings.

P39 — Consumer cannot deserialize payment message post-upgrade

Cause: Serializer/deserializer mismatch (Avro schema not compatible).
Fix: Use Schema Registry with compatibility checks and update consumers first.
Tip: Always do consumer rolling upgrades before producer schema changes.

P40 — Payments slow because of too many small messages (overhead)

Cause: High overhead per message (network/syscalls).
Fix: Batch messages at producer side or compress.
Tip: Use linger.ms to increase batching in low-latency tolerance windows.

P41 — Payment duplicate because of mirror replication loop

Cause: Misconfigured MirrorMaker creating loops between clusters.
Fix: Configure replication filters and cluster aliasing to avoid loops.
Tip: Label clusters clearly and simulate replication topology before production.

P42 — Payments missing during broker rolling upgrade

Cause: Unclean leader election or replication not caught up.
Fix: Perform controlled shutdowns and ensure ISR healthy before upgrade.
Tip: Disable auto leader election for maintenance windows if needed.

P43 — High disk IO on brokers due to compaction of large payment-state topic

Cause: Aggressive compaction on huge datasets.
Fix: Tune cleaner throughput or move compaction to off-peak.
Tip: Put compaction-heavy topics on separate disks/brokers.

P44 — Payment topic partition count insufficient causing consumer starvation

Cause: Too few partitions for required parallelism.
Fix: Increase partitions (be mindful of ordering impact).
Tip: Add partitions with care: consumers need to rebalance and ordering semantics change.

P45 — Payment connector to ledger DB times out under load

Cause: DB cannot handle concurrent sink writes.
Fix: Batch writes in connector, increase DB capacity, or apply rate limiting.
Tip: Use bulk upsert APIs when available.

P46 — Payments audit shows missing fields after connector transform (SMT)

Cause: SMT misconfigured or buggy.
Fix: Test SMTs in staging and validate output schema on sink.
Tip: Version SMT configs and validate with end-to-end fixtures.

P47 — Consumer stuck in restart loop due to poison message at fixed offset

Cause: Specific message causes exception on deserialize/processing.
Fix: Seek past that offset and move message to quarantine; fix producer or transform.
Tip: Expose admin endpoint to move offsets for emergency unblocking.

P48 — Payment pipeline exhibits slow metadata fetchs causing producer backpressure

Cause: Excessive partition counts or frequent topic creation.
Fix: Cache metadata and reduce dynamic topic churn; keep moderate partition counts.
Tip: Use metadata-aware clients to reduce metadata refresh frequency.

P49 — Payment reconciliation runs diverge across environments (staging vs prod)

Cause: Non-deterministic processing or time zone handling.
Fix: Normalize event timestamps to UTC; use deterministic aggregations.
Tip: Compare checksums for partitions when validating behavior.

P50 — Large payment file generation fails due to partial topic consumption

Cause: Not all partitions processed or offsets inconsistent.
Fix: Ensure consumer checkpoints per partition and create barrier sync before file finalize.
Tip: Use a coordination topic to publish partition completion markers.

P51 — Payment DLQ missing original headers/trace info

Cause: DLQ insertion drops headers or metadata.
Fix: Preserve headers when producing to DLQ and include error context.
Tip: Standardize header schema for easier debugging.

P52 — Authorization (ACL) error prevents payment ingestion after rotation

Cause: Service principal permissions revoked after key rotation.
Fix: Rotate ACLs with credential changes and test connectivity.
Tip: Automate ACL updates in CI/CD when rotating keys.

P53 — High network errors for producers in VPC peering

Cause: MTU mismatch or firewall throttling.
Fix: Check MTU, QoS, and firewall; use keepalives and tune socket options.
Tip: Run network perf tests during maintenance windows.

P54 — Payments ingestion SLO violated during GC-heavy periods

Cause: Consumer JVM GC causing stalls.
Fix: GC tuning, smaller heaps, or move to native runtimes (Graal) if applicable.
Tip: Use GC logs and flamegraphs to spot hotspots.

P55 — Payment processing emits inconsistent currency rounding across services

Cause: Different numeric libraries or serialization precision loss.
Fix: Standardize numeric types and serialization (e.g., use cents/integer).
Tip: Document and enforce currency handling in shared libs.

P56 — Producer clients fail due to incompatible client library versions

Cause: Broker/Client version mismatches with deprecated APIs.
Fix: Align client versions or enable compatibility settings.
Tip: Maintain a compatibility matrix in release notes.

P57 — Payments pipeline cannot scale due to JVM thread exhaustion

Cause: Blocking IO inside processing threads.
Fix: Use async IO, reactive frameworks, or increase thread pool with bounded queue.
Tip: Profile thread states during peak to find blocking calls.

P58 — Payment ingestion outage due to accidental topic deletion

Cause: Human error or automation bug.
Fix: Restore from backup/replication or replay from archived source; implement topic-deletion protection.
Tip: Enable topic deletion guard in prod and require approvals.

P59 — Payment schemas diverge between internal teams

Cause: No central governance for schemas.
Fix: Enforce Schema Registry and a schema review process.
Tip: Automate schema compatibility checks in PR pipelines.

P60 — Payment routing logic accidentally sends refunds to live payments topic

Cause: Incorrect routing conditions.
Fix: Implement routing tests and circuit breakers; add topic validation.
Tip: Use topic whitelists for sensitive topics.

P61 — Consumer group stuck due to too-heavy rebalancing caused by flaky network

Cause: Frequent session timeouts.
Fix: Increase session.timeout.ms, stabilize network, or use static memberships.
Tip: Monitor heartbeat misses as early signal.

P62 — Payment data exfiltration risk discovered in Kafka topics

Cause: Lax ACLs or plaintext brokers.
Fix: Enable TLS, SASL, and strict ACLs; audit consumer lists.
Tip: Rotate keys and review ACLs periodically.

P63 — Settlement confirmation duplicate after partial commit by connector

Cause: Sink connector committed offset before sink ack.
Fix: Enable exactly-once semantics for connectors or implement idempotent sink.
Tip: Use transaction-aware sinks where possible.

P64 — Payment risk-scoring stream lags behind feed in busy hours

Cause: Computationally heavy model scoring on single thread.
Fix: Parallelize scoring, use GPU/accelerated inference, or precompute features.
Tip: Use batching and vectorized model inference.

P65 — Payment enrichment cache stale causing wrong routing

Cause: Cache TTL too long or missing updates.
Fix: Use changelog topic for mapping and update cache on change notifications.
Tip: Add cache invalidation signals in pipeline.

P66 — Legacy system writes payments directly to DB and out-of-band to Kafka causing mismatch

Cause: Non-atomic dual-writes.
Fix: Replace with CDC/Outbox from DB as single source.
Tip: Prioritize migration path with shadow writes and reconciliation.

P67 — Payment processing fails for a specific customer only

Cause: Customer-specific data violating assumptions (e.g., large memo field).
Fix: Sanitize inputs, add validation on ingress, handle exceptions gracefully.
Tip: Keep sample failing messages for regression tests.

P68 — Too many small topics for many merchants overloads controller

Cause: Topic explosion (per-merchant topics).
Fix: Multi-tenant partitioning in shared topics or limit topic creation.
Tip: Provide tenant metadata in message rather than per-tenant topics.

P69 — Payment retry policies cause unbounded reprocessing loops

Cause: Retry cycle without failover to DLQ.
Fix: Add max retry count and exponential backoff, then send to DLQ.
Tip: Monitor retry rates and expose reason codes.

P70 — Payments pipeline exposes PII in logs accidentally

Cause: Logging full payloads in error handlers.
Fix: Mask or redact PII in logs and use structured logging with scrubber.
Tip: Keep log redaction rules versioned and covered by tests.

P71 — Payment job fails due to timezone mishandling at EOD aggregation

Cause: Local timezone vs UTC inconsistencies.
Fix: Normalize times to UTC throughout pipeline.
Tip: Store business cut-off in UTC + local offsets documented.

P72 — Payment producer memory leak causes client OOM

Cause: Retained large objects per message or non-closed buffers.
Fix: Profile heap, release resources, avoid large temporary allocations.
Tip: Add memory usage alerts in producers.

## P73 — Payments stall because connector offset pointer corrupted

Cause: Offsets topic corrupted or connector checkpoint bug.
Fix: Reset connector offsets to known good point or recreate connector with snapshot.
Tip: Keep periodic connector config snapshots for quick restore.

## P74 — Fraud detector consumes delayed event and misses window to block transaction

Cause: High consumer lag or late arrival beyond detection window.
Fix: Prioritize fraud topic, scale fraud consumers, or increase detection window with compensations.
Tip: Implement near-real-time alerting for suspicious increases.

## P75 — Payment file consumer duplicates when reprocessing historical data

Cause: Replayed messages processed into sink without dedup.
Fix: Use idempotent writes (upsert) or add processed-run-id logic.
Tip: Version-run markers in DB to ignore historical replays.

## P76 — Unexpected byte-order changes cause corruption in payment serialization across platforms

Cause: Different endianness or protocol mismatch.
Fix: Use platform-agnostic serialization formats and test cross-platform.
Tip: Include version and format identifiers in headers.

## P77 — Payment pipeline consuming from many small partitions causes thread overhead

Cause: High partition count per consumer.
Fix: Consolidate into fewer partitions or increase consumer parallelism.
Tip: Balance partition count across brokers to avoid hotspots.

## P78 — Payment reconciliation job times out when joining with large external dataset

Cause: Join not optimized; external DB slow.
Fix: Use pre-joined snapshots, compacted topic for reference data, or distributed join frameworks.
Tip: Materialize join keys in advance to speed lookups.

## P79 — Settlement fails because connector transaction manager misconfigured

Cause: Connector not using transactional producer settings.
Fix: Enable transactions in connector and ensure isolation.level and transactional.id set.
Tip: Monitor transaction commit failures and retries.

## P80 — Payment producers overwhelmed due to org-wide replay campaign

Cause: Bulk replays from other teams into shared topics.
Fix: Enforce tenant quotas and schedule replays into dedicated maintenance windows.
Tip: Provide a replay sandbox cluster to offload heavy reprocessing.

## P81 — Payment metadata TTL expired before downstream read (missing context)

Cause: Compact topic retention misconfigured.
Fix: Ensure compacted topics for metadata have appropriate retention and don't delete critical keys.
Tip: Use changelog topics for state needed by many consumers.

## P82 — Consumer tasks stuck in long GC during large batch processing

Cause: Processing too many records per poll or huge aggregate objects.
Fix: Reduce max.poll.records, stream process per-record or batch in smaller chunks.
Tip: Use object pooling or streaming parsers to reduce GC churn.

## P83 — Payment ingestion errors spike after a library update

Cause: Breaking change in serialization library.
Fix: Rollback or fix library; add backward compatibility unit tests.
Tip: Use semantic versioning and keep compatibility tests in CI.

## P84 — Payments pipeline logs show high RecordTooLargeException rates

Cause: Occasional oversized messages due to attachments or bad encoding.
Fix: Enforce upper bound in ingress validation; move large payloads to external store.
Tip: Reject or quarantine messages that exceed a safe threshold immediately.

## P85 — Payment consumers starve because of cooperative assignor bug

Cause: Client assignor misconfiguration or client library bug.
Fix: Fall back to sticky/roundrobin assignor; upgrade client libs.
Tip: Test assignor behavior in integration environment.

## P86 — Payment integrity check fails after compression change

Cause: Compression incompatible or corruption in transit.
Fix: Ensure both producers and consumers support chosen compression; test end-to-end.
Tip: Add checksum field to messages for end-to-end verification.

## P87 — Merchant onboarding spike causes many small topics and controller overload

Cause: One topic per merchant approach.
Fix: Multi-tenant topic model with tenant id in message; per-tenant partitions if needed.
Tip: Provide tenant quotas and standard templates for ingestion.

## P88 — Payment reversal messages applied out of order causing incorrect balances

Cause: Reversal and original messages routed to different partitions.
Fix: Use same partition key for original and reversal (payment_id/account_id).
Tip: Include sequence numbers and reconcile by sequence if late.

## P89 — Connector sinks inconsistent due to non-idempotent DB stored procedures

Cause: Stored procedure side effects cause duplicates on retries.
Fix: Refactor to idempotent stored procs or use upsert patterns.
Tip: Keep a dedup transaction log inside DB.

## P90 — Payments pipeline needs feature flag toggling per region causing inconsistency

Cause: Inconsistent flag evaluation across consumers.
Fix: Use central feature flag service and include flag version in message header.
Tip: Roll flags with canary to a subset of consumers first.

## P91 — Broker metadata leader thrash during heavy payment topic creation

Cause: Frequent topic create/delete churn.
Fix: Batch topic creations and introduce quota/approval gateway for new topics.
Tip: Auto-topic creation should be disabled in prod.

## P92 — Payment analytics consumer failing to join per-partition aggregations after partition increase

Cause: Repartition changes without reprocessing state.
Fix: Use state migration or full replay into new topic with required partitions.
Tip: Version output topics to handle topology changes.

## P93 — Payments pipeline reveals intermittent data corruption in payloads

Cause: Binary encoding issues or partial writes.
Fix: Add schema validation, checksums, and reject corrupt messages early.
Tip: Archive corrupt payloads for root-cause analysis.

## P94 — Consumer group cannot keep up due to network interface saturation

Cause: NIC saturated by high throughput.
Fix: Use multiple NICs, increase bandwidth, or co-locate consumers and brokers.
Tip: Monitor per-NIC throughput and plan capacity.

## P95 — Payment event counts mismatch between Kafka and downstream DB over long period

Cause: Silent drops during producer errors or misconfigured connectors.
Fix: Reconcile using archive logs and replay missing events; fix producer error handling.
Tip: Daily automated reconciliation checks between Kafka counts and DB.

## P96 — Payment routing decisions require referential lookups that are slow

Cause: Doing synchronous HTTP/DB calls per-message during consumer processing.
Fix: Preload reference data into compacted topic and local cache.
Tip: Keep TTL and refresh policies for reference caches.

## P97 — Consumer time skew causes incorrect event-time windows in analytics

Cause: Producer clocks unsynced.
Fix: Enforce NTP sync for all producers and consumers and use server-assigned timestamps if needed.
Tip: Include both event and ingestion timestamps in payload.

## P98 — Payment processing blocked due to quota exceeded for a critical tenant

Cause: Misconfigured quotas or sudden tenant spike.
Fix: Increase quota temporarily and investigate rate; apply fair-share policies.
Tip: Implement emergency throttle override for critical business flows.

## P99 — Long-lasting consumer offsets stuck due to immutable offset retention config

Cause: Offsets retention too short and consumer cannot progress after downtime.
Fix: Increase offsets.retention.minutes and add external checkpoints for long-running consumers.
Tip: For long offline consumers, use snapshot/restore approach.

## P100 — Business requests a time-travel replay of payments for a month with 0 downtime

Cause: Need to reprocess without affecting live pipelines.
Fix: Create parallel replay cluster/consumer group, reprocess archived events into separate output topics, and run reconciliation.
Tip: Use versioned topics and keep live and replay results isolated until validated.

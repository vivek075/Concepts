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

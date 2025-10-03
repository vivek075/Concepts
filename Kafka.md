# Kafka Basics

# Q1. What is Apache Kafka and why is it used?

Kafka is a distributed event streaming platform used for high-throughput, low-latency, real-time data pipelines and streaming applications.

Use cases: log aggregation, stream processing, messaging, event sourcing, microservices communication.

# Q2. How is Kafka different from traditional messaging systems like JMS, RabbitMQ?

Kafka is pull-based, whereas JMS is often push-based.

Kafka stores data durably on disk (commit log).

Kafka is designed for high throughput and can handle millions of messages/sec.

Kafka decouples producers and consumers with scalable partitions.

# Q3. What are Kafka Topics and Partitions?

Topic = logical stream of messages.

Partition = split of a topic for parallelism.

Each partition is an ordered immutable log.

# Q4. What is an offset in Kafka?

Offset = unique sequential ID assigned to each message in a partition.

Consumers use offsets to track their position.

# Q5. How does Kafka provide durability?

Messages are persisted on disk.

Replication across multiple brokers.

Configurable acks and min.insync.replicas.
